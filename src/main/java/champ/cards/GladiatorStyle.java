package champ.cards;

import champ.ChampMod;
import champ.powers.GladiatorStylePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GladiatorStyle extends AbstractChampCard {

    public final static String ID = makeID("GladiatorStyle");

    //stupid intellij stuff power, self, uncommon

    public GladiatorStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
       // this.tags.add(ChampMod.OPENER);
        baseMagicNumber = magicNumber = 2;
       // this.tags.add(ChampMod.OPENERGLADIATOR);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GladiatorStylePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}