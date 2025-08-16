package hermit.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.HermitMod;
import hermit.util.TextureLoader;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;

import static hermit.HermitMod.makeRelicOutlinePath;
import static hermit.HermitMod.makeRelicPath;

public class Horseshoe extends CustomRelic implements OnReceivePowerRelic{
    public static final String ID = HermitMod.makeID("Horseshoe");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("horseshoe.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("horseshoe.png"));

    public Horseshoe() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.CLINK);
    }

    @Override
    public boolean onReceivePower(AbstractPower var1, AbstractCreature var2)
    {
        if (var1.ID == WeakPower.POWER_ID || var1.ID == VulnerablePower.POWER_ID || var1.ID == FrailPower.POWER_ID)
        {
            var1.amount = Math.max(var1.amount-1,0);

            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();

            return var1.amount != 0;
        }

        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {

        if (power.ID.equals(WeakPower.POWER_ID) || power.ID.equals(VulnerablePower.POWER_ID) || power.ID.equals(FrailPower.POWER_ID)) {
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
            return (stackAmount - 1);
        }

        return stackAmount;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
