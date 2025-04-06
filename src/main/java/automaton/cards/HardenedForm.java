package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.HardenedFormPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;

public class HardenedForm extends AbstractBronzeCard {

    public final static String ID = makeID("HardenedForm");

    //stupid intellij stuff power, self, rare

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public HardenedForm() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("HardenedForm.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HardenedFormPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}