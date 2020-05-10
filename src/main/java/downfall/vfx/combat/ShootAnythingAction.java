package downfall.vfx.combat;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShootAnythingAction extends AbstractGameAction {

    private ShootAnythingEffect effect;

    public ShootAnythingAction(AbstractCreature target, AbstractCreature source, Texture texture) {
        this(target, source, texture, 15);
    }

    public ShootAnythingAction(AbstractCreature target, AbstractCreature source, Texture texture, int time) {
        this.actionType = ActionType.SPECIAL;

        effect = new ShootAnythingEffect(target, source, texture, time);
        AbstractDungeon.effectList.add(effect);
    }


    public void update() {
        if (effect.finishedAction) {
            this.isDone = true;
        }
    }
}