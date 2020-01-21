package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import theHexaghost.actions.RetractAction;

public class RetreatToShadows extends AbstractHexaCard {

    public final static String ID = makeID("RetreatToShadows");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = -2;

    public RetreatToShadows() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RetractAction());
        atb(new LoseHPAction(p, p, magicNumber));
        applyToSelf(new IntangiblePlayerPower(p, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}