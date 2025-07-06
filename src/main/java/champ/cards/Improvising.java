package champ.cards;

import champ.powers.ImprovisingPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class Improvising extends AbstractChampCard {
    public final static String ID = makeID("Improvising");

    public Improvising() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        postInit();
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, "Improvising.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ImprovisingPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}