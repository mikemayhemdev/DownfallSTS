package champ.cards;

import champ.ChampMod;
import champ.powers.GladiatorStylePower;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.fatigue;

public class GladiatorStyle extends AbstractChampCard {

    public final static String ID = makeID("GladiatorStyle");

    //stupid intellij stuff power, self, uncommon

    public GladiatorStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
       // this.tags.add(ChampMod.OPENER);
        baseMagicNumber = magicNumber = myHpLossCost = 20;
       // this.tags.add(ChampMod.OPENERGLADIATOR);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        fatigue(magicNumber);
        applyToSelf(new StrengthPower(p, 2));
        applyToSelf(new DexterityPower(p, 2));
    }

    public void upp() {
        upgradeMagicNumber(-10);
        myHpLossCost = magicNumber;
    }
}