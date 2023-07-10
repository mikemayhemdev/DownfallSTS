package collector.cards;

import collector.actions.AllEnemyLoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;

public class DarkwoodKindling extends AbstractCollectorCard {
    public final static String ID = makeID(DarkwoodKindling.class.getSimpleName());
    // intellij stuff skill, none, common, , , , , 10, 4

    public DarkwoodKindling() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        atb(new AllEnemyLoseHPAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(4);
    }
}