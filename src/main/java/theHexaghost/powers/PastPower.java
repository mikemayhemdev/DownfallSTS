package theHexaghost.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import theHexaghost.util.OnAdvanceOrRetractSubscriber;
import theHexaghost.util.OnRetractSubscriber;
import downfall.util.TextureLoader;

public class PastPower extends AbstractPower implements CloneablePowerInterface, OnAdvanceOrRetractSubscriber {

    public static final String POWER_ID = HexaMod.makeID("PastPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Past84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Past32.png");
    public int activation_count = 0;

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PastPower(final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        activation_count = 0;
        updateDescription();
    }

    public void onAdvanceOrRetract(boolean endTurn) {
        if (!endTurn) {
            if (activation_count < amount) {
                this.flash();
                addToBot(new DrawCardAction(1));
            }
            activation_count++;
            updateDescription();
        }
    }

    public void updateDescription() {


        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }

        if (activation_count >= this.amount) {
            this.description += DESCRIPTIONS[6];
        } else if ((this.amount - activation_count) > 1) {
            this.description += DESCRIPTIONS[3] + (this.amount - activation_count) + DESCRIPTIONS[4];
        } else {
            this.description += DESCRIPTIONS[3] + (this.amount - activation_count) + DESCRIPTIONS[5];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new PastPower(amount);
    }
}
