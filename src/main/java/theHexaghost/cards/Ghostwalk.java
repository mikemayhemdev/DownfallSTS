package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.RetractAction;
import theHexaghost.powers.EtherealRefundPower;
import theHexaghost.util.CardIgnore;

@CardIgnore
public class Ghostwalk extends AbstractHexaCard {

    public final static String ID = makeID("Ghostwalk");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public Ghostwalk() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new RetractAction());
        applyToSelf(new EtherealRefundPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}