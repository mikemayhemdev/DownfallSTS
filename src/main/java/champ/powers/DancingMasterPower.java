package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.*;
import downfall.util.TextureLoader;

public class DancingMasterPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("DancingMasterPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/DancingMaster84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/DancingMaster32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DancingMasterPower(final int amount) {
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

    private int stanceChangesThisTurn = 0;
    private boolean usedYet = false;

    @Override
    public void atStartOfTurn() {
        stanceChangesThisTurn = 0;
        usedYet = false;
        updateDescription();
    }

    @Override
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!newStance.ID.equals(NeutralStance.STANCE_ID)) {
            stanceChangesThisTurn++;
            if (stanceChangesThisTurn == 3 && !usedYet) {
                flash();
                addToBot(new GainEnergyAction(amount));
                addToBot(new DrawCardAction(amount * 2));
                usedYet = true;
            }
        }
        updateDescription();
    }

    @Override
    public void updateDescription() {
        int x = 3 - stanceChangesThisTurn;
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount * 2 + DESCRIPTIONS[2] + (usedYet ? DESCRIPTIONS[4] : x + DESCRIPTIONS[3]);
    }

    @Override
    public AbstractPower makeCopy() {
        return new DancingMasterPower(amount);
    }
}