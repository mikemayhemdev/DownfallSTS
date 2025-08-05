package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.ui.OrbitingSpells;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class BirdsEye extends AbstractAwakenedCard {
    public final static String ID = makeID(BirdsEye.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public BirdsEye() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(BirdsEye.class.getSimpleName() + ".png"));
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                OrbitingSpells.refreshSpells();
            }
        });
        atb(new ConjureAction(true));
    }

    public void upp() {
        exhaust = false;
    }
}