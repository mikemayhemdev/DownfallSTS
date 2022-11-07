package collector.cards;

import collector.actions.DrawCardFromCollectionAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.isGuarded;
import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class Mindflare extends AbstractCollectorCard {
    public final static String ID = makeID(Mindflare.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Mindflare() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        if (isGuarded()) {
            for (int i = 0; i < magicNumber; i++) {
                atb(new DrawCardFromCollectionAction());
            }
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}