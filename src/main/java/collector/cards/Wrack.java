package collector.cards;

import collector.CollectorMod;
import collector.powers.SoulSnare;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import slimebound.powers.SlimedPower;
import theHexaghost.powers.BurnPower;

public class Wrack extends AbstractCollectorCard {
    public final static String ID = makeID("Wrack");

    public Wrack() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower po : m.powers) {
            if (po.ID.equals(CollectorMod.Afflictions.get(0))){
                atb(new ApplyPowerAction(m,p, new WeakPower(m,po.amount,false)));
            } else if (po.ID.equals(CollectorMod.Afflictions.get(1))){
                atb(new ApplyPowerAction(m,p, new VulnerablePower(m,po.amount,false)));
            } else if (po.ID.equals(CollectorMod.Afflictions.get(2))){
                atb(new ApplyPowerAction(m,p, new PoisonPower(m,p,po.amount)));
            } else if (po.ID.equals(CollectorMod.Afflictions.get(3))){
                atb(new ApplyPowerAction(m,p, new SlimedPower(m,p,po.amount)));
            } else if (po.ID.equals(CollectorMod.Afflictions.get(4))){
                atb(new ApplyPowerAction(m,p, new BurnPower(m,po.amount)));
            }  else if (po.ID.equals(CollectorMod.Afflictions.get(5))){
                atb(new ApplyPowerAction(m,p, new SoulSnare(po.amount)));
            }
        }
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }
}
