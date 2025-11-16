package champ.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import champ.ChampMod;
import champ.patches.SignatureMovePatch;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnRemoveCardFromMasterDeckRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import downfall.util.TextureLoader;

import java.util.function.Predicate;

import static champ.ChampMod.*;

public class SignatureFinisher extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer>, OnRemoveCardFromMasterDeckRelic {

    public static final String ID = ChampMod.makeID("SignatureFinisher");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SignatureMove.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SignatureMove.png"));

    public AbstractCard card = null;
    public boolean cardSelected = true;
    private boolean hasfinisher = false;

    private boolean cardRemoved = false;

    public SignatureFinisher() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (this.card != null && card.uuid.equals(this.card.uuid)) {
            this.flash();
        }
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
        return SignatureMovePatch.inSignatureMove::get;
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
                SignatureMovePatch.inSignatureMove.set(card, true);
                setDescriptionAfterLoading();
                //card.cost = 0;
                //card.costForTurn = 0;
                //card.isCostModified = true;
            }
        }
    }

    @Override
    public void atBattleStartPreDraw() {
        if (!cardRemoved && cardSelected){
            boolean cardExists = false;
            if (card!=null) {
                for(AbstractCard c :AbstractDungeon.player.masterDeck.group){
                    if (c.uuid==card.uuid){
                        cardExists = true;
                        break;
                    }
                }
            }
            if (!cardExists) {
                cardRemoved = true;
                tips.clear();
                this.description = this.DESCRIPTIONS[4];
                initializeTips();
            }
        }
        if (cardRemoved) {
            return;
        }
        super.atBattleStartPreDraw();
        //counter = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (SignatureMovePatch.inSignatureMove.get(card)) {
                //addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
                break;
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
            if (c.hasTag(FINISHER)) {
                tmp.addToTop(c);
            }
        }
        if (tmp.size() > 0) {
            AbstractDungeon.gridSelectScreen.open(tmp,
                    1, DESCRIPTIONS[1] + name + ".",
                    false, false, false, false);
        }
    }

    @Override
    public void onUnequip() {
        if (card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);

            if (cardInDeck != null) {
                SignatureMovePatch.inSignatureMove.set(cardInDeck, false);
            }

        }
    }

    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            cardSelected = true;
            card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
//            card.cost = 0;
//            card.costForTurn = 0;
//            card.isCostModified = true;
            SignatureMovePatch.inSignatureMove.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }


//    @Override
//    public void onRemoveCardFromMasterDeck(AbstractCard var1){
//        if (var1.uuid == card.uuid){
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

            if (!cardExists) {
                tips.clear();
                this.description = this.DESCRIPTIONS[3];
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
    }


    public boolean canSpawn() {
        for (AbstractCard c : CardGroup.getGroupWithoutBottledCards(AbstractDungeon.player.masterDeck).group) {
            if (c.hasTag(FINISHER)) {
                hasfinisher = true;
            }
        }
        return hasfinisher;
    }


    @Override
    public AbstractRelic makeCopy() {
        return new SignatureFinisher();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
