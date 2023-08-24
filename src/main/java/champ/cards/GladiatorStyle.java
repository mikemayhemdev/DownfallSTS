package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.loadJokeCardImage;

public class GladiatorStyle extends AbstractChampCard {
    public final static String ID = makeID("GladiatorStyle");

    public GladiatorStyle() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        postInit();
        loadJokeCardImage(this, "GladiatorStyle.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new DexterityPower(p, magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}