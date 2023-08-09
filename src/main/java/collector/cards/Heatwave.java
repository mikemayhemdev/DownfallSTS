package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class Heatwave extends AbstractCollectorCard {
    public final static String ID = makeID(Heatwave.class.getSimpleName());
    // intellij stuff attack, all_enemy, uncommon, 12, 4, , , , 

    public Heatwave() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 4;
        cardsToPreview = new Ember();
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        makeInHand(new Ember());
    }

    public void upp() {
        upgradeDamage(3);
    }
}