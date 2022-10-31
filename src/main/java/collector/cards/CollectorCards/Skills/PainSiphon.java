package collector.cards.CollectorCards.Skills;

import collector.CollectorMod;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PainSiphon extends AbstractCollectorCard {
    public final static String ID = makeID("PainSiphon");

    public PainSiphon() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        magicNumber = baseMagicNumber = 1;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower po : m.powers) {
            if (CollectorMod.AfflictionMatch(po.ID)) {
                atb(new GainEnergyAction(1));
                atb(new DrawCardAction(1));
            }
        }
    }

    @Override
    public void upp() {
        upgradeBaseCost(1);
    }
}
