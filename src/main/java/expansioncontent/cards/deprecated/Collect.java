package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

@CardIgnore
public class Collect extends AbstractExpansionCard {
    public final static String ID = makeID("Collect");

    private static final int BLOCK = 15;
    private static final int UPGRADE_BLOCK = 5;

    public Collect() {
        super(ID, 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_COLLECTOR);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new GainBlockAction(p, p, this.block));
        atb(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        atb(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

}


