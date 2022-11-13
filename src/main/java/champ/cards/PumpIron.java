package champ.cards;

import champ.powers.PumpIronPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PumpIron extends AbstractChampCard {

    public final static String ID = makeID("PumpIron");

    //stupid intellij stuff power, self, uncommon

    public PumpIron() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);

        baseMagicNumber = magicNumber = 2;
        //tags.add(ChampMod.TECHNIQUE);
        //techniqueLast = true;
        //postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PumpIronPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}