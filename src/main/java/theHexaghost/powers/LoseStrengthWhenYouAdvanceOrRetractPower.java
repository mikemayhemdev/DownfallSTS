package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class LoseStrengthWhenYouAdvanceOrRetractPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = HexaMod.makeID("LoseStrengthWhenYouAdvanceOrRetractPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Key_power32.png");

    public LoseStrengthWhenYouAdvanceOrRetractPower(final int amount) {
        this.name = "LoseStrengthWhenYouAdvanceOrRetract";
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
        description = "Lose #b" + amount + " #yStrength when you #yAdvance or #yRetract.";
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseStrengthWhenYouAdvanceOrRetractPower(amount);
    }
}