package awakenedOne.cards;

import awakenedOne.actions.AddSpellCardAction;
import awakenedOne.cards.tokens.spells.GreaterPyromancy;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class StudyPyromancy extends AbstractAwakenedCard {
    public final static String ID = makeID(StudyPyromancy.class.getSimpleName());
    // intellij stuff attack, all_enemy, common, 7, 3, , , , 

    public StudyPyromancy() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
        isMultiDamage = true;
        cardsToPreview = new GreaterPyromancy();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        addToBot(new AddSpellCardAction(new GreaterPyromancy()));
    }

    public void upp() {
        upgradeDamage(3);
    }
}