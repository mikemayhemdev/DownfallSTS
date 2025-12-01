package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.util.TextureLoader;

public class Incense extends CustomRelic implements OnReceivePowerRelic {
    public static final String ID = CollectorMod.makeID(Incense.class.getSimpleName());
    private static final String IMG_PATH = Incense.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = Incense.class.getSimpleName() + ".png";

    public Incense() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean onReceivePower(AbstractPower beingApplied, AbstractCreature source) {
        if (beingApplied.ID.equals(WeakPower.POWER_ID) || beingApplied.ID.equals(VulnerablePower.POWER_ID) || beingApplied.ID.equals(FrailPower.POWER_ID)) {
            if (AbstractDungeon.player.hasPower(beingApplied.ID)) {
                flash();
                addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                return false;
            }
        }
        return true;
    }
}

