package charbosses.actions.util;

import charbosses.bosses.AbstractCharBoss;
import charbosses.powers.bossmechanicpowers.AbstractBossMechanicPower;
import charbosses.relics.CBR_Calipers;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import downfall.monsters.NeowBoss;

public class CharBossMonsterGroup extends MonsterGroup {


    public CharBossMonsterGroup(AbstractMonster[] input) {
        super(input);
    }

    @Override
    public void applyPreTurnLogic() {
        //downfallMod.logger.info("New Monstergroup preTurnLogic triggered");

        for (AbstractMonster m : this.monsters) {
            if (m instanceof AbstractCharBoss) {
                AbstractCharBoss cB = (AbstractCharBoss) m;
                if (!m.isDying && !m.isEscaping) {
                    for (AbstractPower p : m.powers) {
                        if (p instanceof AbstractBossMechanicPower) {
                            ((AbstractBossMechanicPower) p).PreRoundLoseBlock();
                        }
                    }
                    if (!m.hasPower("Barricade") && !m.hasPower(BlurPower.POWER_ID)) {
                        if (cB.hasRelic(CBR_Calipers.ID)) {
                            //downfallMod.logger.info("Calipers block triggered.");
                            m.loseBlock(15);
                        } else {
                            //downfallMod.logger.info("Normal block loss triggered.");
                            m.loseBlock();
                        }
                    }

                    if (NeowBoss.neowboss != null) {
                        if (NeowBoss.neowboss.offscreen) {
                            //downfallMod.logger.info("Start of turn power: Neow is not null and is offscreen.");
                            m.applyStartOfTurnPowers();
                        } else {
                            //downfallMod.logger.info("Start of turn power: Neow is not null is offscreen.");
                        }
                    } else {
                        //downfallMod.logger.info("Start of turn power: Neow is null.");
                        m.applyStartOfTurnPowers();
                    }
                }
            } else {
                //downfallMod.logger.info("Start of turn power: This is not a charboss.");
                m.applyStartOfTurnPowers();
            }
        }

    }
}
