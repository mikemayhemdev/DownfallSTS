package awakenedOne.cards;

import awakenedOne.powers.ManaburnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.*;

public class WaveOfMiasma extends AbstractAwakenedCard {
    public final static String ID = makeID(WaveOfMiasma.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    //Shroud of Miasma

    public WaveOfMiasma() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 5;
        this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(WaveOfMiasma.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(q -> {
            Wiz.applyToEnemy(q, new WeakPower(q, magicNumber, false));
            Wiz.applyToEnemy(q, new ManaburnPower(q, secondMagic));
        });
    }

    public void upp() {
        //upgradeMagicNumber(2);
        upgradeSecondMagic(4);
    }
}