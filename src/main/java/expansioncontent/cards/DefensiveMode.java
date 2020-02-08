package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import expansioncontent.expansionContentMod;


public class DefensiveMode extends AbstractExpansionCard {
    public final static String ID = makeID("DefensiveMode");

    private static final int BLOCK = 12;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public DefensiveMode() {
        super(ID, 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_GUARDIAN);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new GainBlockAction(p, p, this.block));
        atb(new ApplyPowerAction(p, p, new ThornsPower(p, this.magicNumber), this.magicNumber));
        atb(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

}