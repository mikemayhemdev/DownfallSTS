package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BerserkPower;

public class QuadCore extends AbstractTwinsCard {

    public final static String ID = makeID("QuadCore");

    //stupid intellij stuff power, self, rare

    public QuadCore() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(DonuDecaMod.CORE);
        tags.add(DonuDecaMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BerserkPower(p, 1));
    }

    public void upp() {
        tags.remove(DonuDecaMod.BURNOUT);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}