package hermit.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.HermitMod;
import hermit.relics.PetGhost;
import hermit.util.TextureLoader;

import static hermit.HermitMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class PetGhostPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = HermitMod.makeID(PetGhostPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public PetGhost parent;


    boolean prepDeath;


    public PetGhostPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        prepDeath=false;

        type = PowerType.BUFF;

        this.loadRegion("end_turn_death");

        updateDescription();
    }

    @Override
    public void atStartOfTurn() { // At the start of your turn
        prepDeath=true;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (prepDeath) {
            this.flash();
            parent.canDie=true;
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            this.addToBot(new LoseHPAction(this.owner, this.owner, 99999));
        }
    }


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {

        if (prepDeath)
        description = DESCRIPTIONS[0] + DESCRIPTIONS[2];
        else
        description = DESCRIPTIONS[0] +  DESCRIPTIONS[1];

    }

    @Override
    public AbstractPower makeCopy() {
        return new PetGhostPower(owner, amount);
    }
}
