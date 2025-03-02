package collector.cards;

import collector.powers.NextAttackAppliesDoomPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class Arrogance extends AbstractCollectorCard {
    public final static String ID = makeID(Arrogance.class.getSimpleName());
    // intellij stuff skill, self, common, , , 8, 2, 5, 3

    public Arrogance() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new NextAttackAppliesDoomPower(magicNumber));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}