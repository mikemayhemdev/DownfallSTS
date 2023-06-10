package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.isAfflicted;

public class Extricate extends AbstractCollectorCard {
    public final static String ID = makeID(Extricate.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 9, 3, , , , 

    public Extricate() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (isAfflicted(m)) {
            atb(new PlayTopCardAction(m, false));
        }
    }

    public void upp() {
        upgradeDamage(3);
    }
}