package slimebound.powers;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.relics.AbsorbEndCombat;
import slimebound.relics.AbsorbEndCombatUpgraded;
import slimebound.vfx.FakeFlashAtkImgEffect;
import slimebound.vfx.SlimeDripsEffectPurple;


public class SlimedPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:SlimedPower";
    public static final String NAME = "UsefulSlime";
    public static final String IMG = "powers/SlimedS.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static String[] DESCRIPTIONS;
    public boolean doubleUp = false;
    private AbstractCreature source;


    public SlimedPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;
        this.amount = amount;

        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(slimebound.SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();


    }


    public void updateDescription() {

        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);


    }

    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FakeFlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, new Color(Color.PURPLE), 1F, false, 0.6F)));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(this.owner.hb.cX, this.owner.hb.cY, 4), 0.05F));
        SlimeboundMod.triggerGoopCardVFX();
    }


    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FakeFlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, new Color(Color.PURPLE), 1F, false, 0.6F)));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(this.owner.hb.cX, this.owner.hb.cY, 4), 0.05F));


    }

/*
    public void atStartOfTurn() {

        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(this.owner, this.owner, SlimedPower.POWER_ID, this.amount / 2));

    }
   */


    public float atDamageFinalReceive(float damage, DamageInfo.DamageType damageType) {

        if (damageType == DamageInfo.DamageType.NORMAL) {


            return damage + this.amount;

        }

        return damage;

    }


    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            if (AbstractDungeon.player.hasRelic(AbsorbEndCombat.ID)) {
                AbstractDungeon.player.getRelic(AbsorbEndCombat.ID).onTrigger();
            }

            if (AbstractDungeon.player.hasRelic(AbsorbEndCombatUpgraded.ID)) {
                AbstractDungeon.player.getRelic(AbsorbEndCombatUpgraded.ID).onTrigger();
            }

            if (this.source.hasPower(GoopArmorPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.source, this.source, this.source.getPower(GoopArmorPower.POWER_ID).amount));
                this.source.getPower(GoopArmorPower.POWER_ID).flash();
            }

            if (this.source.hasPower(GoopIntoPoisonPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.source, new PoisonPower(this.owner, this.source, this.source.getPower(GoopIntoPoisonPower.POWER_ID).amount), this.source.getPower(GoopIntoPoisonPower.POWER_ID).amount, true, AbstractGameAction.AttackEffect.POISON));
                this.source.getPower(GoopIntoPoisonPower.POWER_ID).flash();
            }


            SlimeboundMod.checkForEndGoopCardVFX();
            if (this.owner.hasPower(PreventSlimeDecayPower.POWER_ID)) {
                // AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, this.amount / 2));
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.owner.getPower(PreventSlimeDecayPower.POWER_ID), 1));
            } else {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }


            if (this.source.hasPower(GluttonyPower.POWER_ID)) {
                ((GluttonyPower) this.source.getPower(GluttonyPower.POWER_ID)).activate();
            }
        }

        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void onRemove() {
        super.onRemove();
        SlimeboundMod.checkForEndGoopCardVFX();
    }
}





