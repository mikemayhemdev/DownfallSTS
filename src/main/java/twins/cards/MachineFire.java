package twins.cards;

import twins.actions.EasyXCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MachineFire extends AbstractTwinsCard {

    public final static String ID = makeID("MachineFire");

    //stupid intellij stuff skill, enemy, uncommon

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public MachineFire() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (effect, params) -> {
            //TODO: Repeat 1 Blaster effect+params[0] times
            return true;
        }, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}