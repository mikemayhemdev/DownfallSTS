package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.StoneCalendar;
import hermit.HermitMod;
import hermit.powers.RyeStalkPower;
import hermit.util.TextureLoader;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class Horseshoe extends CustomRelic implements OnReceivePowerRelic{

    // ID, images, text.
    public static final String ID = HermitMod.makeID("Horseshoe");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("horseshoe.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("horseshoe.png"));

    public Horseshoe() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }


    /*
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 1 && (damageAmount % 7 == 0)) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            return 1;
        } else {
            return damageAmount;
        }
    }
    */
    @Override
    public boolean onReceivePower(AbstractPower var1, AbstractCreature var2)
    {
        if (var1.ID == WeakPower.POWER_ID || var1.ID == VulnerablePower.POWER_ID || var1.ID == FrailPower.POWER_ID)
        {
            var1.amount = Math.max(var1.amount-1,0);

            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();

            if (var1.amount != 0) {
                return true;
            }

            return false;
        }

        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {

        if (power.ID == WeakPower.POWER_ID || power.ID == VulnerablePower.POWER_ID || power.ID == FrailPower.POWER_ID) {
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
            return (stackAmount - 1);
        }

        return stackAmount;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
