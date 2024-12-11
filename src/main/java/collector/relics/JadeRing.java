package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import collector.powers.DoomPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import downfall.util.TextureLoader;

public class JadeRing extends CustomRelic {
    public static final String ID = CollectorMod.makeID(JadeRing.class.getSimpleName());
    private static final String IMG_PATH = JadeRing.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = JadeRing.class.getSimpleName() + ".png";

    private static final int EXTRA_SOULS = 6;

    public JadeRing() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    //logic moved to DoomPower.java
    //but basically the way it works is that DoomPower has +6 added to it when dealing damage and
    //rendering health bars, and returning the amount of damage in the description

  //  @Override
  //  public void onMonsterDeath(AbstractMonster m) {
   //     if (m.hasPower(DoomPower.POWER_ID) && !m.hasPower(MinionPower.POWER_ID) && !m.halfDead) {
   //         flash();
  //          AbstractDungeon.player.gainGold(EXTRA_SOULS);
 //       }
//    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + EXTRA_SOULS + DESCRIPTIONS[1];
    }
}

