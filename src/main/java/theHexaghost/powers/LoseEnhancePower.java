package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class LoseEnhancePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("LoseEnhancePower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power32.png");

    public LoseEnhancePower(final int amount) {
        this.name = "Lose Enhance";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = "At the end of your turn, lose #b" + amount + " #yEnhance.";
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();// 30
        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EnhancePower(-this.amount), -this.amount));// 31
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));// 32
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseEnhancePower(amount);
    }
}