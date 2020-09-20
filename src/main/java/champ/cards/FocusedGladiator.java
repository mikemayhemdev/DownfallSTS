package champ.cards;

import champ.powers.GladPlusDrawPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FocusedGladiator extends AbstractChampCard {

    public final static String ID = makeID("FocusedGladiator");

    //stupid intellij stuff skill, self, uncommon

    public FocusedGladiator() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        gladiatorOpen();
        applyToSelf(new GladPlusDrawPower(1));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}