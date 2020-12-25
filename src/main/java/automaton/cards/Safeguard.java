package automaton.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Safeguard extends AbstractBronzeCard {

    public final static String ID = makeID("Safeguard");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 15;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = -1;

    public Safeguard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void onCompileToChangeCost(AbstractCard function, boolean forGameplay) {
        function.cost += magicNumber;
        function.costForTurn += magicNumber;
    }

    public void upp() {
        upgradeBlock(5);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}