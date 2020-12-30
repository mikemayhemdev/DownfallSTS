package twins.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import twins.DonuDecaMod;

public class DonuCard extends AbstractTwinsCard {

    public final static String ID = makeID("DonuCard");

    //stupid intellij stuff skill, self, basic

    public DonuCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        tags.add(DonuDecaMod.DONU_CARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upp() {
        upgradeBaseCost(0);
    }
}