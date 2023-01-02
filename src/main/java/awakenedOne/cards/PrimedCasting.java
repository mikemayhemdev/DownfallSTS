package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class PrimedCasting extends AbstractAwakenedCard {
    public final static String ID = makeID(PrimedCasting.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 3, 1, 3, 1

    public PrimedCasting() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new ConjureAction(true, new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : ConjureAction.conjuredCards) {
                    q.upgrade();
                    q.superFlash();
                }
            }
        }));
    }

    public void upp() {
        upgradeBlock(3);
    }
}