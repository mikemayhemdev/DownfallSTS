package slimebound.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.AcidSlime_L;
import com.megacrit.cardcrawl.monsters.exordium.SlimeBoss;
import com.megacrit.cardcrawl.monsters.exordium.SpikeSlime_L;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import slimebound.SlimeboundMod;
import slimebound.powers.SplitDailyTriggerPower;

public class DailySplitModRelic extends CustomRelic {
    public static final String ID = "Slimebound:DailySplitRelic";
    public static final String IMG_PATH = "relics/dailysplitrelic.png";
    public static final String OUTLINE_IMG_PATH = "relics/dailysplitrelicOutline.png";
    private static final int HP_PER_CARD = 1;

    public DailySplitModRelic() {
        super(ID, new Texture(SlimeboundMod.getResourcePath(IMG_PATH)), new Texture(SlimeboundMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void atBattleStart() {

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m.id != AcidSlime_L.ID && m.id != SpikeSlime_L.ID && m.id != SlimeBoss.ID)
                //logger.info("Daily Mod adding buff to " + m.name);
                //this.printEnemies();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new SplitDailyTriggerPower(m, 1), 1));

        }

    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DailySplitModRelic();
    }

}