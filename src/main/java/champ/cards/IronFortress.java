package champ.cards;

import champ.powers.IronFortressPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class IronFortress extends AbstractChampCard {
    public final static String ID = makeID("IronFortress");

    public IronFortress() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        loadJokeCardImage(this, "IronFortress.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new IronFortressPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}