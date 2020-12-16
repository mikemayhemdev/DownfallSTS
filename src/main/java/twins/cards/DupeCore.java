package twins.cards;

import twins.DonuDecaMod;
import twins.powers.FireBonusPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DupeCore extends AbstractTwinsCard {

    public final static String ID = makeID("DupeCore");

    //stupid intellij stuff skill, self, uncommon

    public DupeCore() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(DonuDecaMod.CORE);
        tags.add(DonuDecaMod.BURNOUT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(1));
        applyToSelf(new FireBonusPower(1));
    }

    public void upp() {
        tags.remove(DonuDecaMod.BURNOUT);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}