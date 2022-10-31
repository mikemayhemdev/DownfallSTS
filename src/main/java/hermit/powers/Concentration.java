package hermit.powers;

import basemod.interfaces.AddAudioSubscriber;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hermit.HermitMod;
import hermit.cards.AbstractHermitCard;
import hermit.relics.Spyglass;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class Concentration extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID(Concentration.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("concentration_p.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("concentration.png"));


    public Concentration(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;

        type = PowerType.BUFF;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
            if (card.hasTag(AbstractHermitCard.Enums.DEADON)) {
                this.flash();
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }

    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();

        if (AbstractDungeon.player.hasPower(Concentration.POWER_ID)) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            if (AbstractDungeon.player.hasRelic(Spyglass.ID)) {
                AbstractDungeon.player.getRelic(Spyglass.ID).flash();
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EnergizedPower(this.owner, 1), 1));
            }
        }
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
		description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Concentration(owner, amount);
    }
}
