package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.AwakenDeathPower;


public class AwakenDeath extends AbstractExpansionCard {
    public final static String ID = makeID("AwakenDeath");

    private static final int MAGIC = 20;
    private static final int UPGRADE_MAGIC = 10;

    public AwakenDeath() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(expansionContentMod.STUDY_AWAKENEDONE);
        tags.add(expansionContentMod.STUDY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_awakenedone.png", "expansioncontentResources/images/1024/bg_boss_awakenedone.png");
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
        atb(new ApplyPowerAction(p, p, new AwakenDeathPower(p, p, this.magicNumber), this.magicNumber));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

}


