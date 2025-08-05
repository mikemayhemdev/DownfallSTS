package awakenedOne.cards;

import awakenedOne.powers.ManaburnPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class Gloomguard extends AbstractAwakenedCard {
    public final static String ID = makeID(Gloomguard.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Gloomguard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBlock = 9;
        baseMagicNumber = magicNumber = 3;
        loadJokeCardImage(this, makeBetaCardPath(Gloomguard.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.addToBot(new ApplyPowerAction(m, p, new ManaburnPower(m, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }
}