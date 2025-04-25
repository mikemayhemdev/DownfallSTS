//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package slimebound.actions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Path;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class IncreaseMaxHpFlatAction extends AbstractGameAction {
    private boolean showEffect;
    private int increaseAmount;

    public IncreaseMaxHpFlatAction(AbstractCreature p, int increase, boolean showEffect) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_FAST;
        }

        this.duration = this.startDuration;
        this.showEffect = showEffect;
        this.increaseAmount = increase;
        this.target = p;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (increaseAmount > 0) {
                this.target.increaseMaxHp(increaseAmount, this.showEffect);
            } else {
                this.target.decreaseMaxHealth(increaseAmount);
            }
        }

        this.tickDuration();
    }
}
