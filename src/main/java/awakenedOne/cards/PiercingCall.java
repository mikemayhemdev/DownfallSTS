package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class PiercingCall extends AbstractAwakenedCard {
    public final static String ID = makeID(PiercingCall.class.getSimpleName());
    // intellij stuff attack, all_enemy, uncommon, 12, 4, , , 1, 1

    public PiercingCall() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 12;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
        atb(new ConjureAction(false, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard q : ConjureAction.conjuredCards) {
                    q.setCostForTurn(0);
                    q.superFlash();
                }
            }
        }));
    }

    public void upp() {
        upgradeDamage(4);
    }
}