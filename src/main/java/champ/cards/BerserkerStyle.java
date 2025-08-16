package champ.cards;

import champ.powers.HonePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class BerserkerStyle extends AbstractChampCard {

    public final static String ID = makeID("BerserkerStyle");

    //stupid intellij stuff power, self, uncommon

    public BerserkerStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
       // myHpLossCost = 5;
        postInit();
        loadJokeCardImage(this, "BerserkerStyle.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new HonePower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}