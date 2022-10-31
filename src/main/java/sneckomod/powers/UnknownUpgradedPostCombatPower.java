package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import sneckomod.SneckoMod;
import sneckomod.util.UpgradedUnknownReward;
import downfall.util.TextureLoader;

public class UnknownUpgradedPostCombatPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = SneckoMod.makeID("UnknownUpgradedPostCombatPower");

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/UnknownAfterCombat84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/UnknownAfterCombat32.png");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UnknownUpgradedPostCombatPower(final int amount) {
        this.name = NAME;
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
    public void updateDescription() {
        if (amount > 1)
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        else
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
    }

    @Override
    public void onVictory() {
        for (int i = 0; i < this.amount; i++)
            AbstractDungeon.getCurrRoom().addCardReward(new UpgradedUnknownReward());
    }

    @Override
    public AbstractPower makeCopy() {
        return new UnknownUpgradedPostCombatPower(amount);
    }
}