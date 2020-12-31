package twins.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import twins.TwinsHelper;
import twins.actions.CallToHandAction;

public class Swapper extends AbstractTwinsCard {

    public final static String ID = makeID("Swapper");

    //stupid intellij stuff skill, self, basic

    public Swapper() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!TwinsHelper.getBackCardGroup().isEmpty()) {
            atb(new CallToHandAction(TwinsHelper.getBackCardGroup().getTopCard(), TwinsHelper.getBackCardGroup()));
        }
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                TwinsHelper.swap();
            }
        });
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}