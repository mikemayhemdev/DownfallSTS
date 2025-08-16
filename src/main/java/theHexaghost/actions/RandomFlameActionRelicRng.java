package theHexaghost.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theHexaghost.GhostflameHelper;

public class RandomFlameActionRelicRng extends AbstractGameAction {

    public RandomFlameActionRelicRng(){}

    public void update() {
        isDone = true;
        GhostflameHelper.end_on_random_flame_relic_rng();
    }
}
