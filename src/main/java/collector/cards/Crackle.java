package collector.cards;

import collector.cards.AbstractCollectorCard;
import collector.powers.CracklePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class Crackle extends AbstractCollectorCard {
    public final static String ID = makeID(Crackle.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , , 

    public Crackle() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CracklePower());
    }

    public void upp() {
        isInnate = true;
    }
}