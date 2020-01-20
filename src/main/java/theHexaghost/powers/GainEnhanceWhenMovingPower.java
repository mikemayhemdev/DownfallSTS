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

public class GainEnhanceWhenMovingPower extends AbstractPower implements CloneablePowerInterface, OnAdvanceOrRetractSubscriber {

    public static final String POWER_ID = HexaMod.makeID("GainEnhanceWhenMovingPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power32.png");

    public GainEnhanceWhenMovingPower(final int amount) {
        this.name = "Thermal Planning";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void onAdvanceOrRetract() {
        this.flash();
        addToBot(new ApplyPowerAction(owner, owner, new EnhancePower(amount), amount));
        addToBot(new ApplyPowerAction(owner, owner, new LoseEnhancePower(amount), amount));
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();// 30
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));// 32
    }

    @Override
    public void updateDescription() {
        description = "This turn, when you #yAdvance or #yRetract, gain #b" + amount + " #yMomentary #yEnhance.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new GainEnhanceWhenMovingPower(amount);
    }
}