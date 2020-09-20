package champ.cards;

import champ.powers.FocusedDefPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FocusedDefense extends AbstractChampCard {

    public final static String ID = makeID("FocusedDefense");

    //stupid intellij stuff skill, self, common

    public FocusedDefense() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        applyToSelf(new FocusedDefPower(3));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}