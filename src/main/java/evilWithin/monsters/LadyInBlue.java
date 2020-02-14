package evilWithin.monsters;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import evilWithin.EvilWithinMod;
import evilWithin.powers.FairyPotionPower;
import evilWithin.util.TextureLoader;
import evilWithin.vfx.PotionThrowEffect;
import evilWithin.vfx.combat.ShootAnythingAction;
import slimebound.vfx.SlimeProjectileEffect;

public class LadyInBlue extends AbstractMonster {

    public static final String ID = EvilWithinMod.makeID("LadyInBlue");
    public static final String NAME = "The Woman in Blue";
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 320.0F;

    int turnNum = 0;
    boolean usedDebuffs = false;
    boolean usedEmergency = false;

    public LadyInBlue() {
        super(NAME, ID, 100, HB_X, HB_Y, HB_W, HB_H, "evilWithinResources/images/monsters/default.png");
        switch (AbstractDungeon.actNum) {
            case 1:
                setHp(100);
                break;
            case 2:
                setHp(150);
                break;
            case 3:
                setHp(175);
                break;
        }

        this.damage.add(new DamageInfo(this, 20));
        this.damage.add(new DamageInfo(this, 10));
    }

    public void takeTurn() {
        switch (this.nextMove) {
            case 1:
                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/FirePotion.png", this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 2F, 0.6F, false, false), 0.6F));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.FIRE));

                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/CultistPotion.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                addToBot(new ApplyPowerAction(this, this, new RitualPower(this, 1, false), 1));
                break;
            case 2:
                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/ExplosivePotion.png", this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 2F, 0.6F, false, false), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.NONE));

                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/BlockPotion.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, 12));
                break;
            case 3:
                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/EssenceOfSteel.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 4), 4));

                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/AncientPotion.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new ArtifactPower(this, 1), 1));
                break;
            case 4:
                usedDebuffs = true;
                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/WeakPotion.png", this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 2F, 0.6F, false, false), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 3, true), 3));
                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/FearPotion.png", this.hb.cX, this.hb.cY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 2F, 0.6F, false, false), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 3, true), 3));
                break;
            case 5:
                usedEmergency = true;
                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/GhostInAJar.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new IntangiblePower(this, 1), 1));
                addToBot(new VFXAction(new PotionThrowEffect("evilWithinResources/images/vfx/FairyinaBottle.png", this.hb.cX, this.hb.cY, this.hb.cX, this.hb.cY, 2F, 0.6F, false, true), 0.6F));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FairyPotionPower(this, 1), 1));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.currentHealth <= 0 && this.hasPower(FairyPotionPower.POWER_ID)) {
            this.heal((int) (this.maxHealth / 0.3F), true);
            addToTop(new RemoveSpecificPowerAction(this, this, FairyPotionPower.POWER_ID));
            addToTop(new TalkAction(this, "Ha! Fairy Potion, jerk!"));
        }
    }

    protected void getMove(int num) {
        if (currentHealth <= 0.3F * maxHealth && !usedEmergency) {
            setMove((byte) 5, Intent.BUFF);
        } else {
            switch (turnNum) {
                case 0:
                    this.setMove((byte) 1, Intent.ATTACK_BUFF, this.damage.get(0).base);
                    break;
                case 1:
                    this.setMove((byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
                    break;
                case 2:
                    this.setMove((byte) 3, Intent.BUFF);
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
