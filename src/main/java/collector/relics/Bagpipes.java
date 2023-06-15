package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Bagpipes extends CustomRelic implements OnApplyPowerRelic {
    public static final String ID = CollectorMod.makeID(Bagpipes.class.getSimpleName());
    private static final String IMG_PATH = Bagpipes.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = Bagpipes.class.getSimpleName() + ".png";

    public Bagpipes() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.STARTER, LandingSound.MAGICAL);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onApplyPower(AbstractPower toApply, AbstractCreature target, AbstractCreature source) {
        if (toApply.ID.equals(WeakPower.POWER_ID) || toApply.ID.equals(VulnerablePower.POWER_ID)) {
            if (target != AbstractDungeon.player && source == AbstractDungeon.player) {
                toApply.amount += 1;
            }
        }
        return true;
    }
}

