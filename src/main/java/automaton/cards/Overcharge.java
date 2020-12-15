package automaton.cards;

import automaton.actions.EasyXCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Overcharge extends AbstractBronzeCard {

    public final static String ID = makeID("Overcharge");

    //stupid intellij stuff power, self, rare

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public Overcharge() {
        super(ID, -1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (effect, params) -> {
            applyToSelfTop(new StrengthPower(p, effect + params[0]));
            applyToSelfTop(new DexterityPower(p, effect + params[0]));
            return true;
        }, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}