package collector.cards.CollectorCards.Skills;

import collector.CollectorMod;
import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.Suffering;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Wrack extends AbstractCollectorCard {
    public final static String ID = makeID("Wrack");

    public Wrack() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }
}
