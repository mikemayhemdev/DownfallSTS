package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;
import theHexaghost.HexaMod;
import theHexaghost.util.OnChargeSubscriber;

public class LivingBombPower extends AbstractPower implements CloneablePowerInterface, OnChargeSubscriber {

    public static final String POWER_ID = HexaMod.makeID("LivingBombPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/LivingBomb84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/LivingBomb32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LivingBombPower(AbstractCreature target, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = target;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LivingBombPower(this.owner, this.amount);
    }
}