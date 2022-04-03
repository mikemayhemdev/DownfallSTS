package timeeater.cards.alternateDimension;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;

public class DarkRitual extends AbstractDimensionalCard {
    public final static String ID = makeID("DarkRitual");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public DarkRitual() {
        super(ID, 1, CardType.SKILL, CardTarget.SELF);

        setFrame("darkritualframe.png");
        baseMagicNumber = magicNumber = 15;
        exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LoseHPAction(p, p, magicNumber));
        atb(new GainGoldAction(100));
    }

    public void upp() {
        upgradeMagicNumber(-5);
    }
}