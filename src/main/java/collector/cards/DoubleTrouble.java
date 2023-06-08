package collector.cards;

import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class DoubleTrouble extends AbstractCollectorCard {
    public final static String ID = makeID(DoubleTrouble.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 6, 3, , 

    public DoubleTrouble() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(3);
    }
}