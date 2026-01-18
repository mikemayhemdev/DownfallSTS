package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.ManaburnPower;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.makeRelicOutlinePath;
import static awakenedOne.AwakenedOneMod.makeRelicPath;
import static awakenedOne.util.Wiz.atb;

public class TomeOfPortalmancy extends CustomRelic {

    public static final String ID = AwakenedOneMod.makeID("TomeOfPortalmancy");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("TomeOfPortalmancy.png"));
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("TomeOfPortalmancy.png"));

    private static final int AMOUNT = 2;

    public TomeOfPortalmancy() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    //tome of portalmancy

    public void onSpecificTrigger() {
        this.flash();
        Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while (var2.hasNext()) {
            AbstractMonster mo = (AbstractMonster) var2.next();
            if (!mo.isDead && !mo.isDying) {
                atb(new ApplyPowerAction(mo, AbstractDungeon.player, new ManaburnPower(mo, AMOUNT), AMOUNT));
                this.addToBot(new VFXAction(new GiantEyeEffect(mo.hb.cX, mo.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
                this.addToTop(new RelicAboveCreatureAction(mo, this));
            }
        }

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMOUNT + DESCRIPTIONS[1];
    }

}
