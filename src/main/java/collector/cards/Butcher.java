package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.isAfflicted;

public class Butcher extends AbstractCollectorCard {
    public final static String ID = makeID(Butcher.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 11, 1, , , , 

    public Butcher() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 11;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        if (isAfflicted(m)) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
            dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        }
    }

    public void upp() {
        selfRetain = true;
        upgradeDamage(1);
        uDesc();
    }
}