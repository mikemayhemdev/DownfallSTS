package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

@CardIgnore
public class DecasProtection extends AbstractExpansionCard {
    public final static String ID = makeID("DecasProtection");

    public DecasProtection() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);

        tags.add(expansionContentMod.STUDY_SHAPES);
        tags.add(expansionContentMod.STUDY);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new ApplyPowerAction(p, p, new DexterityPower(p, 1), 1));
        if (upgraded) atb(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1));

        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
        c.setCostForTurn(0);
        atb(new MakeTempCardInHandAction(c, true));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}



