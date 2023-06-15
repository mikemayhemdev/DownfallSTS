package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import collector.powers.DoomPower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JadeRing extends CustomRelic {
    public static final String ID = CollectorMod.makeID(JadeRing.class.getSimpleName());
    private static final String IMG_PATH = JadeRing.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = JadeRing.class.getSimpleName() + ".png";

    private static final int EXTRA_SOULS = 5;

    public JadeRing() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if (m.hasPower(DoomPower.POWER_ID)) {
            flash();
            AbstractDungeon.player.gainGold(EXTRA_SOULS);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EXTRA_SOULS + DESCRIPTIONS[1];
    }
}

