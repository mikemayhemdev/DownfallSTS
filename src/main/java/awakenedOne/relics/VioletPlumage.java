package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static awakenedOne.AwakenedOneMod.*;

public class VioletPlumage extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("VioletPlumage");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("VioletPlumage.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("VioletPlumage.png"));

    //Hey, you! Go to VioletPlumagePatch! This relic also uses code from Aspiration, the GitHub and relevant patch is linked there!

    //the relic was reworked because mini black hole had to go to shop because its weird rip old effect

    //the relic was reworked again

    public VioletPlumage() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    //violet plumage
    public boolean firstTurn = false;
    public boolean activated = false;
//    @Override
//    public void onUseCard(AbstractCard c, UseCardAction action) {
//        if ((c.type == AbstractCard.CardType.POWER) && !this.grayscale){
//            flash();
//            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//            this.addToBot(new GainEnergyAction(1));
//            //addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true));
//            this.grayscale = true;
//        }
//    }


    @Override
    public void atPreBattle() {
        firstTurn = true;
        activated = false;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (this.grayscale = false) {
            if (firstTurn == false) {
                if (activated) {
                    this.flash();
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    addToBot(new DrawCardAction(AbstractDungeon.player, 3));
                    this.grayscale = true;
                }
            }
        }
        this.firstTurn = false;
        activated = false;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (this.grayscale = false) {
            if (EnergyPanel.totalCount > 0) {
                activated = true;
            }
        }
    }

    @Override
    public void atBattleStart() {
        this.grayscale = false;
    }

    public void onVictory() {
        this.grayscale = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 3 + DESCRIPTIONS[1];
    }

}
