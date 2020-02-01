package expansioncontent.cards;



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import expansioncontent.expansionContentMod;

public class DonusPower extends AbstractExpansionCard {
    public final static String ID = makeID("DonusPower");

    public DonusPower() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_SHAPES);
        tags.add(expansionContentMod.STUDY);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        if (upgraded) atb(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1));

        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
        // c.modifyCostForTurn(this.magicNumber * -1);
        c.setCostForTurn(c.cost + (this.magicNumber * -1));
        atb(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(c, true));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
