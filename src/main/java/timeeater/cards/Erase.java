package timeeater.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.EraseAction;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;

public class Erase extends AbstractTimeEaterCard {
    public final static String ID = makeID("Erase");
    // intellij stuff attack, enemy, rare, 8, 3, , , , 

    public Erase() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EraseAction(3, false, false, true, m, new DamageInfo(p, damage, damageTypeForTurn)));
    }

    public void upp() {
        upgradeDamage(3);
    }
}