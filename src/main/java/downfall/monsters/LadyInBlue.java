package downfall.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import downfall.downfallMod;
import downfall.powers.FairyPotionPower;
import downfall.vfx.PotionThrowEffect;

public class LadyInBlue extends AbstractMonster {

    public static final String ID = downfallMod.makeID("LadyInBlue");
    public static final String NAME = CardCrawlGame.languagePack.getEventString("The Woman in Blue").NAME;
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 320.0F;

    int turnNum = 0;
    boolean usedDebuffs = false;
    boolean usedEmergency = false;


    public LadyInBlue() {
        super(NAME, ID, 120, HB_X, HB_Y, HB_W, HB_H, "downfallResources/images/monsters/womaninblue/womaninblue2.png");

        this.loadAnimation("downfallResources/images/monsters/womaninblue/WomanInBlue.atlas", "downfallResources/images/monsters/womaninblue/WomanInBlue.json", 1.0F);

        switch (AbstractDungeon.actNum) {
            case 1:
                setHp(150);
                break;
            case 2:
                setHp(200);
                break;
            case 3:
                setHp(250);
                break;
        }

        this.damage.add(new DamageInfo(this, 20));
        this.damage.add(new DamageInfo(this, 10));



        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        this.stateData.setMix("Hit", "Idle", 0.2F);
        this.stateData.setMix("Attack", "Idle", 0.2F);
        this.state.setTimeScale(0.8F);
        this.type = EnemyType.ELITE;
    }


    @Override
    public void changeState(String stateName) {
        switch(stateName) {
            case "ATTACK":
                this.state.setAnimation(0, "Attack", false);
                this.state.addAnimation(0, "Idle", true, 0.0F);
                break;
        }

    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/FirePotion.png", this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 2F, 0.6F, false, false), 0.6F));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));

                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/CultistPotion.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                addToBot(new ApplyPowerAction(this, this, new RitualPower(this, 1, false), 1));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/ExplosivePotion.png", this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 2F, 0.6F, false, false), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.NONE));

                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/BlockPotion.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 12));
                break;
            case 3:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/EssenceOfSteel.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 4), 4));

                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/AncientPotion.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1), 1));
                break;
            case 4:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                usedDebuffs = true;
                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/WeakPotion.png", this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 2F, 0.6F, false, false), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 3, true), 3));
                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/FearPotion.png", this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 2F, 0.6F, false, false), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 3, true), 3));
                break;
            case 5:
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                usedEmergency = true;
                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/GhostInAJar.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, 1), 1));
                addToBot(new VFXAction(new PotionThrowEffect("downfallResources/images/vfx/FairyinaBottle.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FairyPotionPower(this, 1), 1));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }

    protected void getMove(int num) {
        if (currentHealth <= 0.5F * maxHealth && !usedEmergency) {
            setMove((byte) 5, Intent.BUFF);
        } else {
            switch (turnNum) {
                case 0:
                    this.setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
                    break;
                case 1:
                    this.setMove((byte) 3, Intent.BUFF);
                    break;
                case 2:
                    this.setMove((byte) 1, Intent.ATTACK_BUFF, this.damage.get(0).base);
                    break;
                case 3:
                    if (!usedDebuffs)
                        this.setMove((byte) 4, Intent.STRONG_DEBUFF);
                    else {
                        turnNum--;
                        getMove(num);
                    }
                    break;
            }
            this.turnNum++;
            if (this.turnNum == (usedDebuffs ? 3 : 4)) {
                this.turnNum = 0;
            }
        }
    }
}
