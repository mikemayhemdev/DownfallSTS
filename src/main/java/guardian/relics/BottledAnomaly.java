package guardian.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import guardian.GuardianMod;
import guardian.patches.BottledStasisPatch;
import sneckomod.cards.unknowns.AbstractUnknownCard;

import java.util.function.Predicate;

public class BottledAnomaly extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {
    public static final String ID = "Guardian:BottledAnomaly";
    public static final String IMG_PATH = "relics/bottledAnomaly.png";
    public static final String OUTLINE_IMG_PATH = "relics/bottledAnomalyOutline.png";
    public AbstractCard card = null;
    private boolean cardSelected = true;
    private boolean cardRemoved = false;

    public BottledAnomaly() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SHOP, LandingSound.CLINK);
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return BottledStasisPatch.inBottledAnomaly::get;
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
                BottledStasisPatch.inBottledAnomaly.set(card, true);
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
                BottledStasisPatch.inBottledAnomaly.set(cardInDeck, false);
            }
        }
    }

    @Override
    public void update() {
        super.update();
        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            BottledStasisPatch.inBottledAnomaly.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();

            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));

            setDescriptionAfterLoading();
        }

    }

//    @Override
//    public void onRemoveCardFromMasterDeck(AbstractCard var1){
//        if (var1.uuid == card.uuid) {
//            setDescriptionAfterLoading();
//        }
//    }


    public void setDescriptionAfterLoading() {
        boolean cardExists = false;

        if (cardSelected) {
            if (card != null) {
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c.uuid == card.uuid) {
                        cardExists = true;
                        break;
                    }
                }
            }
        }

        if (!cardExists) {
            cardRemoved = true;
            tips.clear();
            this.grayscale = true;
            this.description = this.DESCRIPTIONS[4];
            tips.add(new PowerTip(name, description));
            initializeTips();
        }

        if (cardExists) {
            this.grayscale = false;
            this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
            tips.clear();
            tips.add(new PowerTip(name, description));
            initializeTips();
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledAnomaly();
    }

    @Override
    public void atBattleStartPreDraw() {
        if (!cardRemoved && cardSelected){
            boolean cardExists = false;
            if(card!=null){
                for(AbstractCard c :AbstractDungeon.player.masterDeck.group){
                    if (c.uuid==card.uuid){
                        cardExists = true;
                        break;
                    }
                }
            }
            if (!cardExists){
                cardRemoved = true;
                tips.clear();
                this.description = this.DESCRIPTIONS[4];
                tips.add(new PowerTip(name, description));
                initializeTips();
            }
        }
        if (cardRemoved) {
            return;
        }
        super.atBattleStartPreDraw();
        counter = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.uuid == card.uuid) {
                AbstractDungeon.player.drawPile.removeCard(c);
                break;
            }
        }
    }

    @Override
    public void atTurnStartPostDraw() {
        if (cardRemoved) {
            return;
        }
        if (!this.grayscale) {
            ++this.counter;
        }
        if (counter == 3) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            if (card instanceof AbstractUnknownCard) {
                AbstractCard unknowncard = ( (AbstractUnknownCard)card ).generateFromPoolButNotIntoHand();
                unknowncard.setCostForTurn(0);
                addToBot( new MakeTempCardInHandAction( unknowncard ) );
            }else {
                card.setCostForTurn(0);
                addToBot(new MakeTempCardInHandAction(card));
            }
            this.grayscale = true;
            this.counter = -1;
        }
    }

    @Override
    public void onVictory() {
        counter = -1;
        grayscale = false;
    }

}
