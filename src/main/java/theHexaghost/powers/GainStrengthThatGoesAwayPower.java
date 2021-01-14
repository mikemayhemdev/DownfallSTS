package theHexaghost.powers;

import basemod.ReflectionHacks;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.TheHexaghost;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.util.OnAdvanceOrRetractSubscriber;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class GainStrengthThatGoesAwayPower extends AbstractPower implements CloneablePowerInterface, OnAdvanceOrRetractSubscriber {

    public static final String POWER_ID = HexaMod.makeID("GainStrengthThatGoesAwayPower");

    private static final Texture tex84 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Present84.png");
    private static final Texture tex32 = TextureLoader.getTexture(HexaMod.getModID() + "Resources/images/powers/Present32.png");
    public boolean activated = true;

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int strengthGranted = 0;
    private float timer;

    public GainStrengthThatGoesAwayPower(final int amount) {
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
    public void update(int slot) {
        super.update(slot);
        if (AbstractDungeon.player instanceof TheHexaghost)
            if (
                    (TheHexaghost.startingFlame == GhostflameHelper.activeGhostFlame &&
                            (!GhostflameHelper.activeGhostFlame.charged || AbstractDungeon.player.hasPower(AgainPower.POWER_ID)))
                            ||
                            //If you are on an ignited one and the NEXT flame is the one you started at, also ping this.
                            (TheHexaghost.startingFlame == GhostflameHelper.getNextGhostFlame() &&
                                    GhostflameHelper.activeGhostFlame.charged &&
                                    !AbstractDungeon.player.hasPower(AgainPower.POWER_ID))
            ) {

                if (this.timer <= 0F) {
                    ArrayList<AbstractGameEffect> effect2 = (ArrayList<AbstractGameEffect>) ReflectionHacks.getPrivate(this, AbstractPower.class, "effect");
                    effect2.add(new GainPowerEffect(this));
                    this.timer = 1F;
                } else {
                    this.timer -= Gdx.graphics.getDeltaTime();
                }
            }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void playApplyPowerSfx() {
        //to prevent the 'last turn' warning from pinging audio all the time
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
            if (AbstractDungeon.player instanceof TheHexaghost)
                if (TheHexaghost.startingFlame == GhostflameHelper.activeGhostFlame) {
                    this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, this.amount), this.amount));
                }
        super.atEndOfTurn(isPlayer);
    }

    @Override
    public AbstractPower makeCopy() {
        return new GainStrengthThatGoesAwayPower(amount);
    }
}