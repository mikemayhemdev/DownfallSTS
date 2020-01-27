package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class EnhancePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("EnhancePower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance32.png");

    public EnhancePower(final int amount) {
        this.name = "Intensity";
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;// 39
        if (this.amount == 0) {// 41
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));// 42
        }
    }// 52

    @Override
    public void updateDescription() {
        description = "Increase the amount of Damage, Burn, and Block applied by Ghostflames when Ignited by #b" + amount + ".";
    }

    @Override
    public AbstractPower makeCopy() {
        return new EnhancePower(amount);
    }
}