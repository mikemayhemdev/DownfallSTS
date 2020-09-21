package champ.cards;

import champ.powers.PushThroughPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IgnorePain extends AbstractChampCard {

    public final static String ID = makeID("IgnorePain");

    //stupid intellij stuff skill, self, rare

    public IgnorePain() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        applyToSelf(new PushThroughPower(1));
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}