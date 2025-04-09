package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class BringItOn extends AbstractChampCard {
    public final static String ID = makeID("BringItOn");

    public BringItOn() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        baseMagicNumber = magicNumber = 7;
        tags.add(ChampMod.FINISHER);
        postInit();
        loadJokeCardImage(this, "BringItOn.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new CounterPower(magicNumber));
        finisher();
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(2);
    }
}