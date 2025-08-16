package hermit.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.HermitMod;
import hermit.util.TextureLoader;

public class Drained extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID("Drained");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture("hermitResources/images/powers/hold_barred_p.png");
    private static final Texture tex32 = TextureLoader.getTexture("hermitResources/images/powers/hold_barred.png");

    public Drained(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() { // At the start of your turn
        this.flash();
        this.addToBot(new LoseEnergyAction(amount));
        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, Drained.POWER_ID));

        updateDescription();
    }

    public void updateDescription() {

            description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Drained(owner, source, amount);
    }
}
