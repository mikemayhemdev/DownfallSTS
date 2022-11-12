package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PumpIron extends AbstractChampCard {

    public final static String ID = makeID("PumpIron");

    //stupid intellij stuff power, self, uncommon

    public PumpIron() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        //tags.add(ChampMod.TECHNIQUE);
        //techniqueLast = true;
        //postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO - At the end of each turn, gain 4 Vigor.
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}