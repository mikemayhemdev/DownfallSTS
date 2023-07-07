package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class Roast extends AbstractCollectorCard {
    public final static String ID = makeID(Roast.class.getSimpleName());
    // intellij stuff attack, enemy, common, 9, 3, , , , 

    public Roast() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE); //TODO: We'll need a lot more than just this one fire attack FX. Let's check out hermit's tricks and do some of our own in the long run
    }

    public void upp() {
        upgradeDamage(3);
    }
}