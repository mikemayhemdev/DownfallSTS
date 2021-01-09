/*

package guardian.summons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import guardian.GuardianMod;
import guardian.powers.*;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

public class BronzeOrbWhoReallyLikesDefectForSomeReason extends AbstractFriendlyMonster {

    public static String NAME = "Bronze Orb";
    public static String ID = "Guardian:BronzeOrbWhoReallyLikesDefectForSomeReason";

    public static final int HP = 30;
    public static final int BASEDAMAGE = 12;

    private Float targetX;

    public BronzeOrbWhoReallyLikesDefectForSomeReason(int offsetX, int offsetY) {
        super(NAME, ID, HP, 0F, 20F, 120.0F, 120.0F, "images/monsters/theCity/automaton/orb.png", offsetX, offsetY);

        addMoves();

        this.powers.add(new BronzeOrbLocationPower(this));

        if (AbstractDungeon.player.hasPower(DefenseModePower.POWER_ID) || AbstractDungeon.player.hasPower(ConstructModePower.POWER_ID)) {
            this.moveToBackline();
        } else {
            this.moveToFrontline();
        }


        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new BronzeOrbProtectionPower(AbstractDungeon.player)));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("AUTOMATON_ORB_SPAWN", MathUtils.random(-0.1F, 0.1F)));


    }

    public void takeTurn()
    {
        AbstractMonster abstractMonster = AbstractDungeon.getRandomMonster();

        if (this.hasPower(BronzeOrbBlockPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, this, this.getPower(BronzeOrbBlockPower.POWER_ID).amount));
        }
        if (this.hasPower(BronzeOrbDamagePower.POWER_ID)) {
            DamageInfo damageInfo = this.damage.get(0);
            damageInfo.base = this.getPower(BronzeOrbDamagePower.POWER_ID).amount;
            damageInfo.genPreview(this, abstractMonster);
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.SKY)));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffect(this.hb.cX, this.hb.cY + 30F * Settings.scale, abstractMonster.hb.cX, abstractMonster.hb.cY), 0.3F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(abstractMonster, damageInfo, AbstractGameAction.AttackEffect.NONE));
            if (this.hasPower(BronzeOrbWeakenPower.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractMonster, this, new WeakPower(abstractMonster, this.getPower(BronzeOrbWeakenPower.POWER_ID).amount, false), this.getPower(BronzeOrbWeakenPower.POWER_ID).amount));

            }
        }
        if (this.hasPower(BronzeOrbExplodePower.POWER_ID)){
            ((BronzeOrbExplodePower)this.getPower(BronzeOrbExplodePower.POWER_ID)).updateExplode();
        }

    }

    public void moveToFrontline(){
        this.targetX = 600F * Settings.scale;
        if (this.hasPower(BronzeOrbLocationPower.POWER_ID)){
            ((BronzeOrbLocationPower)this.getPower(BronzeOrbLocationPower.POWER_ID)).moveToFrontline();
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(BronzeOrbProtectionPower.POWER_ID)){
            BronzeOrbProtectionPower bP = (BronzeOrbProtectionPower)p.getPower(BronzeOrbProtectionPower.POWER_ID);
            if (!bP.isActive){
                bP.setActive();
            }

        } else {
            BronzeOrbProtectionPower newPower = new BronzeOrbProtectionPower(p);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,newPower));

        }

    }

    public void moveToBackline(){
        this.targetX = 200F * Settings.scale;
        if (this.hasPower(BronzeOrbLocationPower.POWER_ID)){
            ((BronzeOrbLocationPower)this.getPower(BronzeOrbLocationPower.POWER_ID)).moveToBackline();
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(BronzeOrbProtectionPower.POWER_ID)){
            BronzeOrbProtectionPower bP = (BronzeOrbProtectionPower)p.getPower(BronzeOrbProtectionPower.POWER_ID);
            if (bP.isActive){
                bP.setInactive();
            }

        } else {
            BronzeOrbProtectionPower newPower = new BronzeOrbProtectionPower(p);
            newPower.setInactive();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,newPower));

        }
    }

    @Override
    public void update() {
        super.update();
        if (this.drawX != this.targetX){
            this.drawX = MathHelper.orbLerpSnap(this.drawX, AbstractDungeon.player.animX + this.targetX);
        }
    }

    @Override
    public void applyEndOfTurnTriggers() {
        super.applyEndOfTurnTriggers();
        this.takeTurn();
    }

    protected void getMove(int num)
    {

        setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
        }


    private void addMoves() {
        MOVES = new String[]{};
        DIALOG = new String[] {""};
        this.damage.add(new DamageInfo(this,BASEDAMAGE));

    }

    @Override
    public void die() {
        super.die();
        GuardianMod.bronzeOrbInPlay = null;
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,BronzeOrbProtectionPower.POWER_ID));
    }
}

*/