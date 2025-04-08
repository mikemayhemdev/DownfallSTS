package champ.cards;

import champ.ChampMod;
import champ.powers.BerserkerStylePower;
import champ.powers.DefensiveStylePower;
import champ.powers.FocusedDefPower;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

import static champ.ChampMod.fatigue;

public class DefensiveStyle extends AbstractChampCard {

    public final static String ID = makeID("DefensiveStyle");

    //stupid intellij stuff power, self, uncommon

    public DefensiveStyle() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        //myHpLossCost = 5;
        postInit();
        loadJokeCardImage(this, "DefensiveStyle.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BerserkerStylePower(magicNumber));
        applyToSelf(new DefensiveStylePower(magicNumber));
    }

    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBaseCost(1);
    }
}