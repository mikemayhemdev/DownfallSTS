package collector.cards;

import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelfTop;
import static collector.util.Wiz.atb;

public class Bonfire extends AbstractCollectorCard implements OnPyreCard {
    public final static String ID = makeID(Bonfire.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , 20, 5, , 

    public Bonfire() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 16;
        isPyre();
    }

    int toGrant;

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                applyToSelfTop(new NextTurnReservePower(toGrant));
            }
        });
    }

    @Override
    public void onPyred(AbstractCard card) {
        if (card.costForTurn > 0 && !card.freeToPlay()) {
            toGrant = card.costForTurn;
        }
    }

    public void upp() {
        upgradeBlock(4);
    }
}