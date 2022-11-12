package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlawlessTechnique extends AbstractChampCard {

    public final static String ID = makeID("FlawlessTechnique");

    //stupid intellij stuff power, self, uncommon

    public FlawlessTechnique() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        //tags.add(ChampMod.TECHNIQUE);
        //techniqueLast = true;
        //postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO - Gain E after playing Riposte.
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}