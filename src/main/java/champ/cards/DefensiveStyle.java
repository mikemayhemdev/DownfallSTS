package champ.cards;

import champ.powers.BerserkerStylePower;
import champ.powers.DefensiveStylePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class DefensiveStyle extends AbstractChampCard {

    public final static String ID = makeID("DefensiveStyle");

    //stupid intellij stuff power, self, uncommon

    public DefensiveStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        //myHpLossCost = 5;
        postInit();
        loadJokeCardImage(this, "DefensiveStyle.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BerserkerStylePower(magicNumber));
        applyToSelf(new DefensiveStylePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
        //upgradeBaseCost(1);
    }
}