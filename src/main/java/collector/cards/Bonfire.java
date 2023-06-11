package collector.cards;

import collector.cards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class Bonfire extends AbstractCollectorCard {
    public final static String ID = makeID(Bonfire.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 20, 5, , 

    public Bonfire() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 20;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(5);
    }
}