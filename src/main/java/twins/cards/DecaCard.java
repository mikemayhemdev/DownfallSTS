package twins.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import twins.DonuDecaMod;

public class DecaCard extends AbstractTwinsCard {

    public final static String ID = makeID("DecaCard");

    //stupid intellij stuff skill, self, starter

    public DecaCard() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        tags.add(DonuDecaMod.DECA_CARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upp() {
        upgradeBaseCost(0);
    }
}