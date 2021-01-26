package theTodo.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class NextTurnPowerPower extends AbstractEasyPower {
    private AbstractPower powerToGain;
        /*
    private static Texture arrow48 = TexLoader.getTexture("todomodResources/images/ui/arrow48.png");
    private static Texture arrow128 = TexLoader.getTexture("todomodResources/images/ui/arrow128.png");
    public static HashMap<String, TextureAtlas.AtlasRegion> bufferHashMap48 = new HashMap<>();
    public static HashMap<String, TextureAtlas.AtlasRegion> bufferHashMap128 = new HashMap<>();
    */

    public NextTurnPowerPower(AbstractCreature owner, AbstractPower powerToGrant) {
        super("Next Turn " + powerToGrant.name, powerToGrant.type, false, owner, powerToGrant.amount);
        this.img = powerToGrant.img;
        this.region48 = powerToGrant.region48;
        this.region128 = powerToGrant.region128;
        /*
        this.region48 = bufferHashMap48.computeIfAbsent(powerToGrant.ID, (x) -> {
            SpriteBatch sb = new SpriteBatch();
            FrameBuffer fb = FBHelper.createBuffer(48, 48);
            FBHelper.beginBuffer(fb);
            sb.begin();
            sb.draw(powerToGrant.region48, 0, 0);
            sb.draw(arrow48, 0, 0);
            sb.end();
            fb.end();
            Texture t = FBHelper.getBufferTexture(fb).getTexture();
            return new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
        });
        this.region128 = bufferHashMap128.computeIfAbsent(powerToGrant.ID, (x) -> {
            SpriteBatch sb = new SpriteBatch();
            FrameBuffer fb = FBHelper.createBuffer(128, 128);
            FBHelper.beginBuffer(fb);
            sb.begin();
            sb.draw(powerToGrant.region128, 0, 0);
            sb.draw(arrow128, 0, 0);
            sb.end();
            fb.end();
            Texture t = FBHelper.getBufferTexture(fb).getTexture();
            return new TextureAtlas.AtlasRegion(t, 0, 0, t.getWidth(), t.getHeight());
        });
        */
        this.powerToGain = powerToGrant;
        updateDescription();
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb, x, y, Color.GREEN.cpy());
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        powerToGain.amount += stackAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, powerToGain, powerToGain.amount));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
    }

    @Override
    public void updateDescription() {
        if (powerToGain == null) {
            description = "???";
        } else {
            description = "Next turn, gain #b" + powerToGain.amount + " " + powerToGain.name + ".";
        }
    }
}
