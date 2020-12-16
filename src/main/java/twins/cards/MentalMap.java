package twins.cards;

import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MentalMap extends AbstractTwinsCard {

    public final static String ID = makeID("MentalMap");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public MentalMap() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new ScryAction(magicNumber));
        atb(new BetterDiscardPileToHandAction(1));
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
        upgradeMagicNumber(UPG_MAGIC);
    }
}