package evilWithin.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import evilWithin.EvilWithinMod;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class FairyPotionPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = EvilWithinMod.makeID("FairyPotionPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Enhance32.png");

    public FairyPotionPower(final AbstractCreature owner, final int amount) {
        this.name = "Potion Stash...";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = "She's prepared for anything!";
    }

    @Override
    public AbstractPower makeCopy() {
        return new FairyPotionPower(owner, amount);
    }
}