package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class PeerThrough extends AbstractAwakenedCard {
    public final static String ID = makeID(PeerThrough.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 1, 1

    public PeerThrough() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ScryAction(magicNumber));
        atb(new DrawCardAction(1, new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : DrawCardAction.drawnCards) {
                    q.setCostForTurn(0);
                    q.superFlash();
                }
            }
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}