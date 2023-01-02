package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.awakenedAmt;

public class Manifestation extends AbstractAwakenedCard {
    public final static String ID = makeID(Manifestation.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 22, 6, , , , 

    public Manifestation() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 22;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int amt = awakenedAmt();
        if (amt >= 3) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        }
    }

    public void upp() {
        upgradeDamage(6);
    }
}