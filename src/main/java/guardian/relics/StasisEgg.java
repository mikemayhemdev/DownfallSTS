package guardian.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.GuardianMod;
import guardian.patches.BottledStasisPatch;

import java.util.function.Predicate;

import static awakenedOne.AwakenedOneMod.DELVE;

public class StasisEgg extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer>, OnRemoveCardFromMasterDeckRelic {
    public static final String ID = "Guardian:StasisEgg";
    public static final String IMG_PATH = "relics/stasisEgg.png";
    public static final String OUTLINE_IMG_PATH = "relics/stasisEggOutline.png";
    private boolean cardSelected = true;
    public AbstractCard card = null;

    public StasisEgg() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public void onRemoveCardFromMasterDeck(AbstractCard var1) {
        if (this.card != null) {
            if (var1.uuid == card.uuid) {
                this.flash();
                this.grayscale = true;
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {

        return BottledStasisPatch.inStasisEgg::get;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractCard getCard() {
        return card.makeCopy();
    }

    @Override
    public Integer onSave() {
        return AbstractDungeon.player.masterDeck.group.indexOf(card);
    }

    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (card != null) {
                BottledStasisPatch.inStasisEgg.set(card, true);
                setDescriptionAfterLoading();
            }
        }
    }

    @Override
    public void onEquip() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            tmp.addToTop(c);
        }
        AbstractDungeon.gridSelectScreen.open(tmp,
                1, DESCRIPTIONS[1] + name + ".",
                false, false, false, false);
    }

    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                BottledStasisPatch.inStasisEgg.set(cardInDeck, false);
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottledStasisPatch.inStasisEgg.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));

            setDescriptionAfterLoading();
        }
    }

    private void setDescriptionAfterLoading() {
        boolean cardExists = false;

            if (card != null) {
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.uuid == card.uuid) {
                        cardExists = true;
                        break;
                    }
                }
            }

            if (!cardExists) {
                tips.clear();
                this.description = this.DESCRIPTIONS[4];
                this.grayscale = true;
                initializeTips();
            }

            if (cardExists) {
                this.description = FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[2];
                tips.clear();
                tips.add(new PowerTip(name, description));
                initializeTips();
                this.grayscale = false;
            }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new StasisEgg();
    }

    @Override
    public void atBattleStartPreDraw() {
        super.atBattleStartPreDraw();
        for (AbstractCard ca : AbstractDungeon.player.drawPile.group) {
            if (this.card.uuid == ca.uuid) {
                AbstractDungeon.player.drawPile.removeCard(ca);
                break;
            }
        }
        this.counter = 4;
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (this.counter > 0) {
            this.counter--;
            if (this.counter == 0) {
                this.flash();
                card.current_x = this.currentX;
                card.current_y = this.currentY;

                AbstractCard copy = card.makeStatEquivalentCopy();
                AbstractCard copy2 = card.makeStatEquivalentCopy();
                AbstractCard copy3 = card.makeStatEquivalentCopy();

                copy.current_x = card.current_x;
                copy.current_y = card.current_y;
                copy2.current_x = card.current_x;
                copy2.current_y = card.current_y;
                copy3.current_x = card.current_x;
                copy3.current_y = card.current_y;

                AbstractDungeon.player.hand.addToTop(copy);
                AbstractDungeon.player.hand.addToTop(copy2);
                AbstractDungeon.player.hand.addToTop(copy3);
            }
        }

    }

    public boolean canSpawn() {

        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
                tmp.addToTop(c);
        }

        if (tmp.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

}