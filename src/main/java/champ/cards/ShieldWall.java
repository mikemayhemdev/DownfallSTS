package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;

import static champ.ChampMod.loadJokeCardImage;

public class ShieldWall extends AbstractChampCard {
    public final static String ID = makeID("ShieldWall");

    public ShieldWall() {
        super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        postInit();
        loadJokeCardImage(this, "ShieldWall.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(p, 2));
        applyToSelf(new MetallicizePower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}