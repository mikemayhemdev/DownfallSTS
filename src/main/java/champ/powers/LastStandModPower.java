package champ.powers;

import basemod.ReflectionHacks;
import basemod.interfaces.CloneablePowerInterface;
import champ.ChampMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GainPowerEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class LastStandModPower extends AbstractPower implements CloneablePowerInterface {

    public static final String POWER_ID = ChampMod.makeID("LastStandMod");

    private static final Texture tex84 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/LastStandMod84.png");
    private static final Texture tex32 = TextureLoader.getTexture(ChampMod.getModID() + "Resources/images/powers/LastStandMod32.png");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private float timer;

    public LastStandModPower(AbstractCreature m, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = m;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        this.updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.currentHealth < this.owner.maxHealth / 2) {

            AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_CHAMP_CHARGE"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new InflameEffect(this.owner), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new InflameEffect(this.owner), 0.1F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(this.owner, new InflameEffect(this.owner), 0.1F));

            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction(this.owner));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner, this.owner, "Shackled"));

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        super.atStartOfTurn();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if (this.owner.currentHealth < this.owner.maxHealth / 2){
            if (this.timer <= 0F){
                ArrayList<AbstractGameEffect> effect2 = (ArrayList<AbstractGameEffect>) ReflectionHacks.getPrivate(this, AbstractPower.class, "effect");
                effect2.add(new GainPowerEffect(this));
                this.timer = 1F;
            } else {
                this.timer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void playApplyPowerSfx() {
        //to prevent the 'last turn' warning from pinging audio all the time
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new LastStandModPower(owner, amount);
    }
}