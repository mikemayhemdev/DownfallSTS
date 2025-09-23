package awakenedOne.cards;

import awakenedOne.powers.ManaburnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.*;

public class WaveOfMiasma extends AbstractAwakenedCard {
    public final static String ID = makeID(WaveOfMiasma.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    //Shroud of Miasma

    public WaveOfMiasma() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseSecondMagic = secondMagic = 3;
        baseBlock = 12;
        this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(WaveOfMiasma.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        Wiz.forAllMonstersLiving(q -> {
            Wiz.applyToEnemy(q, new ManaburnPower(q, secondMagic));
        });
    }

    public void upp() {
        //upgradeMagicNumber(2);
        upgradeBlock(3);
        upgradeSecondMagic(2);
    }
}