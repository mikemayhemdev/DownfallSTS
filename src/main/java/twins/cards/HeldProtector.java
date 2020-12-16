package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class HeldProtector extends AbstractTwinsCard {

    public final static String ID = makeID("HeldProtector");

    //stupid intellij stuff skill, self, uncommon

    public HeldProtector() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(DonuDecaMod.SHIELD);
        tags.add(DonuDecaMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (inFire) {
            applyToSelf(new ArtifactPower(p, 1));
        }
    }

    public void upp() {
        tags.remove(DonuDecaMod.BURNOUT);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}