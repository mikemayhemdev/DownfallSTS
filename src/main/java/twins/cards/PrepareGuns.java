package twins.cards;

import twins.TwinsHelper;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrepareGuns extends AbstractTwinsCard {

    public final static String ID = makeID("PrepareGuns");

    //stupid intellij stuff skill, self, uncommon

    public PrepareGuns() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = TwinsHelper.blasters.size();
        atb(new GainEnergyAction(x));
    }

    public void upp() {
        exhaust = false;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}