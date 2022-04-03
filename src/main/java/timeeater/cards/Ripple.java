package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.TurnBasedSlowPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Ripple extends AbstractTimeEaterCard {
    public final static String ID = makeID("Ripple");
    // intellij stuff skill, self, rare, , , 12, , 1, 1

    public Ripple() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 12;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        forAllMonstersLiving(q -> {
            applyToEnemy(q, new WeakPower(q, magicNumber, false));
            applyToEnemy(q, new VulnerablePower(q, magicNumber, false));
            applyToEnemy(q, new TurnBasedSlowPower(q, magicNumber));
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}