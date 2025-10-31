package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.ui.AwakenButton;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class BirdsEye extends AbstractAwakenedCard {
    public final static String ID = makeID(BirdsEye.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public BirdsEye() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(BirdsEye.class.getSimpleName() + ".png"));
        this.tags.add(AwakenedOneMod.DELVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    OrbitingSpells.refreshSpells();
                    AwakenButton.awaken(5);
                }
            });
        }
        atb(new ConjureAction(true));
    }

    public void upp() {
    }
}