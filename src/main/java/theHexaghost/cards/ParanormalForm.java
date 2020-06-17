package theHexaghost.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;
import theHexaghost.powers.ParanormalFormPower;

public class ParanormalForm extends AbstractHexaCard {

    public final static String ID = makeID("ParanormalForm");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 2;

    public ParanormalForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ParanormalFormPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}