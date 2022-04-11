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
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
       // this.tags.add(ChampMod.OPENER);
      baseMagicNumber = magicNumber = 2;
       // this.tags.add(ChampMod.OPENERGLADIATOR);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        applyToSelf(new StrengthPower(p, magicNumber));
       // applyToSelf(new DexterityPower(p, 2));
        for (AbstractMonster q : monsterList()) {
            applyToEnemy(q, new StrengthPower(q, 1));
            //applyToEnemy(q, new DexterityPower(q, 2));
        }

    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}