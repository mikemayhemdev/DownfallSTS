package awakenedOne.cards;

import awakenedOne.actions.EasyXCostAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class LastResort extends AbstractAwakenedCard {
    public final static String ID = makeID(LastResort.class.getSimpleName());
    // intellij stuff attack, enemy, common, 6, 2, , , , 

    public LastResort() {
        super(ID, -1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        if (energyOnUse == 0) {
            exhaust = true;
        }
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (effect == 0) {
                att(new GainEnergyAction(1));
            } else {
                for (int i = 0; i < effect; i++) {
                    att(new GainBlockAction(p, block));
                }
            }
            return true;
        }));
    }

    public void upp() {
        upgradeBlock(2);
    }
}