package awakenedOne.cards.tokens.spells;

import awakenedOne.actions.AllEnemyLoseHPAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class Darkleech extends AbstractSpellCard {
    public final static String ID = makeID(Darkleech.class.getSimpleName());
    // intellij stuff skill, all_enemy, , , , , 7, 2

    public Darkleech() {
        super(ID, CardType.SKILL, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 7;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AllEnemyLoseHPAction(magicNumber));
        atb(new DrawCardAction(secondMagic));
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }
}