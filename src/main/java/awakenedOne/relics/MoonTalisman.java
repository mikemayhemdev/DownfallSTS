package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cardmods.ConjureMod;
import awakenedOne.patches.MoonTalismanPatch;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import java.util.function.Predicate;

import static awakenedOne.AwakenedOneMod.*;

public class MoonTalisman extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer>, OnRemoveCardFromMasterDeckRelic {

    public static final String ID = AwakenedOneMod.makeID("MoonTalisman");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("MoonTalisman.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("MoonTalisman.png"));
    public AbstractCard card = null;
    public boolean cardSelected = false;
    private boolean cardRemoved = false;

    //Don't forget me.

    public MoonTalisman() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
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
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (MoonTalismanPatch.inBottleTalisman.get(card)) {
            this.flash();
            //added a modifier instead
            //atb(new ConjureAction(false));
        }
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return MoonTalismanPatch.inBottleTalisman::get;
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
                MoonTalismanPatch.inBottleTalisman.set(card, true);
            }
            setDescriptionAfterLoading();
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
            if (!c.hasTag(DELVE) && c.cost != -2) {
                tmp.addToTop(c);
            }
        }
        AbstractDungeon.gridSelectScreen.open(tmp,
                1, DESCRIPTIONS[1] + name + LocalizedStrings.PERIOD,
                false, false, false, false);
    }

    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if (cardInDeck != null) {
                AbstractCard copy = cardInDeck.makeStatEquivalentCopy();

                CardModifierManager.removeModifiersById(cardInDeck, ConjureMod.ID, true);
                MoonTalismanPatch.inBottleTalisman.set(cardInDeck, false);

//                for (AbstractRelic r : AbstractDungeon.player.relics) {
//                    if (r instanceof MoonTalisman) {
//                        AbstractDungeon.player.loseRelic(r.relicId);
//                    }
//                }
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(copy));

                CardCrawlGame.sound.play("CARD_EXHAUST");
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(copy, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && card.uuid == null) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);

            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);

            //Note: This is the only source of this modifier on the entire character.
            //If you detect this modifier being used, it's almost definitely from this bottle.
            CardModifierManager.addModifier(cardInDeck, new ConjureMod());

            cardRemoved = false;

            MoonTalismanPatch.inBottleTalisman.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;

            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));

            setDescriptionAfterLoading();
        }
    }

    public void setDescriptionAfterLoading() {

        boolean cardExists = false;

            if (card != null) {
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.uuid == card.uuid) {
                        cardSelected = true;
                        cardExists = true;
                        break;
                    }
                }
            }

            if (!cardExists) {
                cardRemoved = true;
                tips.clear();
                this.description = this.DESCRIPTIONS[4];
                this.grayscale = true;
                tips.add(new PowerTip(name, description));
                initializeTips();
                return;
            }

            if (cardExists) {
                cardRemoved = false;
                this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
                this.grayscale = false;
                tips.clear();
                tips.add(new PowerTip(name, description));
                initializeTips();
            }
        }


    @Override
    public AbstractRelic makeCopy() {
        return new MoonTalisman();
    }

    public boolean canSpawn() {

        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            //considered && c.rarity != CardRarity.BASIC); but decided against it
            if (!c.hasTag(DELVE) && c.cost != -2) {
                tmp.addToTop(c);
            }
        }

        if (tmp.size() > 0) {
            return true;
        } else {
            return false;
        }

    }

}
