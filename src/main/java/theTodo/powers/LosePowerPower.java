package theTodo.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LosePowerPower extends AbstractEasyPower {
    private AbstractPower powerToLose;
    //private static Texture chain = TexLoader.getTexture("todomodResources/images/ui/chain.png");

    public LosePowerPower(AbstractCreature owner, AbstractPower powerToLose, int amount) {
        super("Lose " + powerToLose.name, PowerType.DEBUFF, false, owner, amount);
        this.img = powerToLose.img;
        this.region48 = powerToLose.region48;
        this.region128 = powerToLose.region128;
        this.powerToLose = powerToLose;
        updateDescription();
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.RED.cpy());
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ReducePowerAction(owner, owner, powerToLose.ID, amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (powerToLose == null) {
            description = "???";
        } else {
            description = "At the end of your turn, lose #b" + amount + " " + powerToLose.name + ".";
        }
    }
}
