package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class Contemplate extends AbstractCollectorCard {
    public final static String ID = makeID("Contemplate");
    // intellij stuff skill, self, basic, , , , , , 

    public Contemplate() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upp() {
    }
}