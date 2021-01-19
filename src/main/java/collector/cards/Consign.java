package collector.cards;

import collector.powers.SoulSnare;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Consign extends AbstractCollectorCard {
    public final static String ID = makeID("Consign");
    public int iniHP;
    public Consign() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        damage = baseDamage = 16;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        iniHP = m.currentHealth;
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                if (m.currentHealth < iniHP){
                    atb(new ApplyPowerAction(m,p,new SoulSnare(iniHP - m.currentHealth,m)));
                }
                isDone = true;
            }
        });
    }

    @Override
    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }
}
