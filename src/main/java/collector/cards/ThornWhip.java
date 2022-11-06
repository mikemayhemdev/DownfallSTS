package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.order;

public class ThornWhip extends AbstractCollectorCard {
    public final static String ID = makeID("ThornWhip");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public ThornWhip() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    @Override
    public void triggerOnExhaust() {
        order();
        order();
    }

    public void upp() {
        upgradeDamage(2);
    }
}