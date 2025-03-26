package sneckomod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;

public class OverwhelmingPresencePower extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = SneckoMod.makeID("OverwhelmingPresencePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/EtherealRefund84.png");
    private static final Texture tex32 = TextureLoader.getTexture(SneckoMod.getModID() + "Resources/images/powers/EtherealRefund32.png");

    private int previousDrawPileSize;
    private boolean hasTriggeredThisTurn;

    public OverwhelmingPresencePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.previousDrawPileSize = AbstractDungeon.player.drawPile.size();
        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        // Reset tracking to detect a new shuffle each turn
        this.previousDrawPileSize = AbstractDungeon.player.drawPile.size();
        hasTriggeredThisTurn = false;
    }

    @Override
    public void update(int slot) {
        super.update(slot);

        // Detect shuffle by checking if the draw pile size has increased (refilled from discard)
        int currentDrawPileSize = AbstractDungeon.player.drawPile.size();
        int discardPileSize = AbstractDungeon.player.discardPile.size();

        if (!hasTriggeredThisTurn && currentDrawPileSize > previousDrawPileSize && discardPileSize == 0) {
            // Shuffle detected and effect not yet triggered this turn
            flash();
            addToBot(new GainEnergyAction(amount));
            addToBot(new DrawCardAction(AbstractDungeon.player, amount));

            // Prevent re-triggering this turn
            hasTriggeredThisTurn = true;
        }

        // Update the previousDrawPileSize for the next check
        previousDrawPileSize = currentDrawPileSize;
    }

    @Override
    public void updateDescription() {
        this.description = (amount == 1)
                ? DESCRIPTIONS[0]
                : DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    }

    @Override
    public AbstractPower makeCopy() {
        return new OverwhelmingPresencePower(this.owner, this.amount);
    }
}
