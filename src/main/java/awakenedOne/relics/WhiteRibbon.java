package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import awakenedOne.util.Wiz;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class WhiteRibbon extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("WhiteRibbon");
    private static final int AMOUNT = 3;
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("WhiteRibbon.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("WhiteRibbon.png"));

    public WhiteRibbon() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }


//    @Override
//    public void atBattleStart() {
//        this.counter = -1;
//    }
//
//    @Override
//    public void atTurnStart() {
//        this.counter = -1;
//        beginLongPulse();
//    }
//
//    @Override
//    public void onVictory(){
//        this.counter = -1;
//        stopPulse();
//    }


    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(CHANT)) {
            this.flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, AMOUNT));
        }
    }

//    public void onTrigger() {
//        flash();
//        addToBot(new GainBlockAction(AbstractDungeon.player, AMOUNT));
//    }

//    //Check AwakenButton.java. I'm just using this override for convenience.
//    @Override
//    public void onAwaken(int amount) {
//        if (this.counter == -1) {
//            if (amount == 1) {
//                this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//                //this should provide a little more visual feedback
//                if (Settings.FAST_MODE) {
//                    this.addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.0F));
//                } else {
//                    this.addToTop(new VFXAction(new MiracleEffect(Color.CYAN, Color.PURPLE, "ATTACK_MAGIC_SLOW_1"), 0.3F));
//                }
//                stopPulse();
//                this.counter = 1;
//            }
//        }
//    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
