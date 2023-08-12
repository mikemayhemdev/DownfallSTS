package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import collector.powers.MoreBlockWithTempHPPower;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;

import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class ThimbleHelm extends CustomRelic {
    public static final String ID = CollectorMod.makeID(ThimbleHelm.class.getSimpleName());
    private static final String IMG_PATH = ThimbleHelm.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = ThimbleHelm.class.getSimpleName() + ".png";

    private static final int STARTING_TEMP_HP = 3;

    public ThimbleHelm() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        flash();
        atb(new AddTemporaryHPAction(AbstractDungeon.player, AbstractDungeon.player, STARTING_TEMP_HP));
        applyToSelf(new MoreBlockWithTempHPPower(1));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + STARTING_TEMP_HP + DESCRIPTIONS[1];
    }
}

