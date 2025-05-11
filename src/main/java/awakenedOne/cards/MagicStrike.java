package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class MagicStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(MagicStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public MagicStrike() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 4;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (!upgraded) {
            atb(new ConjureAction(false));
        }

        if (upgraded) {
            atb(new ConjureAction(true));
        }
    }

    public void upp() {
    }
}