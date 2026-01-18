package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.TexLoader;
import awakenedOne.util.Wiz;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;

public class CursedBlessing extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("CursedBlessing");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("CursedBlessing.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("CursedBlessing.png"));


    //required triggers
    private static final int AMOUNT1 = 3;
    //strength gain
    private static final int AMOUNT2 = 1;

    public CursedBlessing() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
        //   DuvuStrings = CardCrawlGame.languagePack.getRelicStrings(DuVuDoll.ID);
        this.counter = -1;
    }

    //Final Rites


//    public void onEquip() {
//        this.counter = 0;
//    }

    //optional anti stalling tech

    public void onEquip() {
        this.counter = -1;
    }

    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + AMOUNT1 + DESCRIPTIONS[1] + AMOUNT2 + DESCRIPTIONS[2];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (Wiz.isChantActive()) {
            if (this.counter != -1) {
                this.counter++;
                if (this.counter == AMOUNT1) {
                    this.counter = -1;
                    flash();
                    this.flash();
                    this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RitualPower(AbstractDungeon.player, AMOUNT2, true), AMOUNT2));
                }
            }
        }
    }

}