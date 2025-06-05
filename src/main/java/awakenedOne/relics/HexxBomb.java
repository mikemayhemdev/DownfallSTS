package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.ManaburnPower;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class HexxBomb extends CustomRelic{

    //Hexx Bomb

    private static final int AMOUNT = 1;

    public static final String ID = AwakenedOneMod.makeID("HexxBomb");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("HexxBomb.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("HexxBomb.png"));

    public HexxBomb() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }


//    @Override
//    public void atBattleStart() {
//        onTrigger();
//    }

//    @Override
//    public void onTrigger() {
//        super.onTrigger();
//        this.flash();
//        //this.addToBot(new DelayedCurseAction);
//        AbstractMonster mo = AbstractDungeon.getRandomMonster();
//        HexCurse(AMOUNT, mo, AbstractDungeon.player);
//        this.addToBot(new VFXAction(new GiantEyeEffect(mo.hb.cX, mo.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
//        this.addToTop(new RelicAboveCreatureAction(mo, this));
//    }


    public void onMonsterDeath(AbstractMonster m) {
        if (m.hasPower(ManaburnPower.POWER_ID)) {
            int amount = m.getPower(ManaburnPower.POWER_ID).amount;
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(m, this));
                AbstractMonster mo = AbstractDungeon.getRandomMonster();
                HexCurse(AMOUNT, mo, AbstractDungeon.player);
                this.addToTop(new RelicAboveCreatureAction(mo, this));
            }
        }

    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
