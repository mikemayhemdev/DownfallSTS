package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import theHexaghost.actions.BurnAction;
import theHexaghost.util.TextureLoader;

public class ApplyBurnAtTurnStartOncePower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("ApplyBurnAtTurnStartOncePower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ABAT84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/ABAT32.png");

    public ApplyBurnAtTurnStartOncePower(final int amount) {
        this.name = "Burn At Start Of Turn";
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
    public void atStartOfTurnPostDraw() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new BurnAction(m, amount));
        }
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = "At the start of your next turn, apply #b" + amount + " #yBurn to ALL enemies.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new ApplyBurnAtTurnStartOncePower(amount);
    }
}