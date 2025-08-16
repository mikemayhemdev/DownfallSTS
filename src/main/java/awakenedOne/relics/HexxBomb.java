package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.ManaburnPower;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.monsters.NeowBoss;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static downfall.patches.EvilModeCharacterSelect.evilMode;

public class HexxBomb extends CustomRelic {

    //Manabomb

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
                this.addToTop(new RelicAboveCreatureAction(m, this));
                AbstractMonster mo = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.relicRng);
                //I don't know WHY this crashes against the act 4 elite.
                if (!(mo instanceof NeowBoss)) {
                    if (!(mo == null)) {
                        this.addToBot(new RelicAboveCreatureAction(mo, this));
                        this.addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new ManaburnPower(mo, amount), amount, true));
                    }
                }
            }
        }
    }

    @Override
    public boolean canSpawn() {
        return Settings.isEndless || ((AbstractDungeon.floorNum <= 53 && AbstractDungeon.ascensionLevel >= 20) && !evilMode) || ((AbstractDungeon.floorNum <= 52 && AbstractDungeon.ascensionLevel < 20 && !evilMode)) || ((AbstractDungeon.floorNum <= 48));
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
