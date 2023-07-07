package collector.cards;

import collector.actions.DrawCardFromCollectionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class ItMattersNot extends AbstractCollectorCard {
    public final static String ID = makeID(ItMattersNot.class.getSimpleName());
    // intellij stuff skill, self, common, , , 8, 3, , 

    public ItMattersNot() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardFromCollectionAction());
    }

    public void upp() {
        upgradeBlock(3);
    }
}