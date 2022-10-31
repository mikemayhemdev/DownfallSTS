package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

@CardIgnore
public class DefensiveStance extends AbstractExpansionCard {
    public final static String ID = makeID("DefensiveStance");

    private static final int BLOCK = 16;
    private static final int UPGRADE_BLOCK = 4;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 1;

    public DefensiveStance() {
        super(ID, 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new GainBlockAction(p, p, this.block));
        atb(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber), this.magicNumber));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

}


