package automaton.cards;

import automaton.powers.CopyPastePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CopyPaste extends AbstractBronzeCard {

    public final static String ID = makeID("CopyPaste");

    //stupid intellij stuff skill, self, rare

    public CopyPaste() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CopyPastePower(1));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}