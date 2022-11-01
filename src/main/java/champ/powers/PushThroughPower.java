package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;

public class PushThroughPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("PushThroughPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/PushThrough84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/PushThrough32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PushThroughPower(final int amount) {
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
    public void atEndOfRound() {
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return 0;
    }

    @Override
    public void updateDescription() {
        if (amount == 1) description = DESCRIPTIONS[0];
        else
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PushThroughPower(amount);
    }
}