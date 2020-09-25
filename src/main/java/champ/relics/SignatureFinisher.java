package champ.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.cards.Defend;
import champ.cards.Strike;
import champ.patches.SignatureMovePatch;
import champ.powers.CalledShotPower;
import champ.stances.AbstractChampStance;
import champ.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.NeutralStance;
import guardian.orbs.StasisOrb;
import guardian.patches.BottledStasisPatch;
import guardian.relics.BottledStasis;
import sneckomod.util.ExhaustMod;

import java.util.function.Predicate;

import static champ.ChampMod.*;

public class SignatureFinisher extends CustomRelic implements CustomBottleRelic, CustomSavable<Integer> {

    public static final String ID = ChampMod.makeID("SignatureFinisher");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SignatureMove.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SignatureMove.png"));

    public AbstractCard card = null;
    private boolean cardSelected = true;
    
    public SignatureFinisher() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
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
            SignatureMovePatch.inSignatureMove.set(card, true);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }

    private void setDescriptionAfterLoading() {
        this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SignatureFinisher();
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.hasTag(ChampMod.FINISHER)){
            if (AbstractDungeon.player.stance instanceof NeutralStance){
                if (card != null){
                    if (!c.purgeOnUse) {
                        AbstractCard r = card.makeStatEquivalentCopy();
                        r.purgeOnUse = true;
                        addToBot(new AbstractGameAction() {
                            @Override

                            //TODO: Still can't quite get this working if you bottle a targeted Finisher, but cast Bring It ON or anything else self-targeted.  The queued extra card doesn't target an enemy.
                            public void update() {
                                isDone = true;
                                if (m == null){
                                    if (c.target == AbstractCard.CardTarget.SELF){
                                        AbstractMonster m2 = AbstractDungeon.getRandomMonster();
                                        GameActionManager.queueExtraCard(r, m2);
                                    } else {
                                        GameActionManager.queueExtraCard(r, m);
                                    }
                                } else {
                                    GameActionManager.queueExtraCard(r, m);
                                }
                            }
                        });
                        if (!AbstractDungeon.player.hasPower(CalledShotPower.POWER_ID)) {
                            addToBot(new PressEndTurnButtonAction());
                        } else {
                            AbstractDungeon.player.getPower(CalledShotPower.POWER_ID).onSpecificTrigger();
                        }
                    }
                }
            }
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
