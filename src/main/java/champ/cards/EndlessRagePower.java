package champ.cards;

import champ.powers.WorseRupturePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EndlessRagePower extends AbstractChampCard {

    public final static String ID = makeID("EndlessRagePower");

    //stupid intellij stuff power, self, uncommon

    public EndlessRagePower() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new WorseRupturePower(1));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}