package champ.cards;

import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static champ.ChampMod.loadJokeCardImage;

public class HoldFirm extends AbstractChampCard {
    public final static String ID = makeID("HoldFirm");

    public HoldFirm() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 10;
        magicNumber = baseMagicNumber = 10;
        postInit();
        loadJokeCardImage(this, "HoldFirm.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new CounterPower(magicNumber));
        applyToSelf(new BlurPower(p, 1));
    }

    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(3);
    }
}