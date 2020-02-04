package expansioncontent.cards;



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import expansioncontent.actions.CorruptAction;
import expansioncontent.expansionContentMod;


public class Corrupt extends AbstractExpansionCard {
    public final static String ID = makeID("Corrupt");


    public Corrupt() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);

        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    atb(new CorruptAction(1,false,false,false,upgraded));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}


