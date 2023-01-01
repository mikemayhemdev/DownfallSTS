package awakenedOne.powers;

import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

public class FromNothingPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = FromNothingPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public FromNothingPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
    }

    private static ArrayList<String> zeroCosters = null;

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();

            for (int i = 0; i < this.amount; ++i) {
                if (zeroCosters == null) {
                    zeroCosters = new ArrayList<>();
                    Wiz.getCardsMatchingPredicate(c -> c.cost == 0).forEach(c -> zeroCosters.add(c.cardID));
                }
                this.addToBot(new MakeTempCardInHandAction(CardLibrary.getCard(Wiz.getRandomItem(zeroCosters)).makeCopy()));
            }
        }

    }

    @Override
    public void updateDescription() {
        if (amount == 0) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}