package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import slimebound.powers.NextTurnGainStrengthPower;

@CardIgnore
public class ChargeUp extends AbstractExpansionCard {
    public final static String ID = makeID("ChargeUp");

    private static final int BLOCK = 20;
    private static final int UPGRADE_BLOCK = 10;
    private static final int MAGIC = 2;

    public ChargeUp() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_GUARDIAN);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new SFXAction("MONSTER_GUARDIAN_DESTROY"));
        atb(new ApplyPowerAction(p, p, new NextTurnBlockPower(p, this.block), this.block));
        atb(new ApplyPowerAction(p, p, new NextTurnGainStrengthPower(p, p, this.magicNumber), this.magicNumber));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

}


