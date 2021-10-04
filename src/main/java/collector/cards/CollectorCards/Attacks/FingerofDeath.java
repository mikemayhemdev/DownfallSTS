package collector.cards.CollectorCards.Attacks;

import collector.cards.CollectorCards.AbstractCollectorCard;
import collector.powers.Suffering;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FingerofDeath extends AbstractCollectorCard {
    public final static String ID = makeID("FingerofDeath");

    public FingerofDeath() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 12;
        magicNumber = baseMagicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
        for (int i = 0; i < magicNumber; i++) {
            applyToEnemy(m,new Suffering(1,m));
        }
    }
    public void triggerOnExhaust() {
        for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
            for (AbstractPower p:m.powers) {
                if (p instanceof Suffering){
                    atb(new GainEnergyAction(1));
                }
            }
        }
        AbstractCard copy = this.makeStatEquivalentCopy();
        copy.magicNumber += 1;
        copy.rawDescription = ((AbstractCollectorCard)copy).EXTENDED_DESCRIPTION[0];
        atb(new MakeTempCardInDiscardAction(copy,1));
    }
    @Override
    public void upp() {
        upgradeDamage(3);
    }
}