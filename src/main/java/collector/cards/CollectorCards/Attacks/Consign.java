package collector.cards.CollectorCards.Attacks;

import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.Suffering;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Consign extends AbstractCollectorCard {
    public final static String ID = makeID("Consign");
    public int iniHP;
    public Consign() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 16;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageCallbackAction(m,new DamageInfo(p,damage), AbstractGameAction.AttackEffect.FIRE, (i)->{atb(new ApplyPowerAction(m,p,new Suffering(i,m)));}));
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }
}
