package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;

public class GhostWheel extends AbstractExpansionCard {
    public final static String ID = makeID("GhostWheel");

    private static final int BLOCK = 20;
    private static final int UPGRADE_BLOCK = 10;
    private static final int MAGIC = 2;

    public GhostWheel() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_HEXAGHOST);
        tags.add(expansionContentMod.STUDY);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (HexaMod.renderFlames) {

            atb(new ApplyPowerAction(p, p, new EnhancePower(1), 1));
        } else {

            HexaMod.renderFlames = true;
        }


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

}


