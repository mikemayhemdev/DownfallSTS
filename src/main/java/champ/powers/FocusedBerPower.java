package champ.powers;

import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import champ.stances.BerserkerStance;
import champ.util.OnTechniqueSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import theHexaghost.HexaMod;
import theHexaghost.util.TextureLoader;

public class FocusedBerPower extends AbstractPower implements CloneablePowerInterface, OnTechniqueSubscriber {

    public static final String POWER_ID = ChampMod.makeID("FocusedBerPower");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/FocusBerserker84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/FocusBerserker32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FocusedBerPower(final int amount) {
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
    public void onChangeStance(AbstractStance oldStance, AbstractStance newStance) {
        if (!newStance.ID.equals(BerserkerStance.STANCE_ID))
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onTechnique() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new ResolvePower(amount), amount));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        ChampMod.updateTechniquesInCombat();
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        ChampMod.updateTechniquesInCombat();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FocusedBerPower(amount);
    }
}