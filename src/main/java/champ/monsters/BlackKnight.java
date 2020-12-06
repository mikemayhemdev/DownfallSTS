 package champ.monsters;

 import com.badlogic.gdx.math.MathUtils;
 import com.esotericsoftware.spine.AnimationState;
 import com.esotericsoftware.spine.AnimationState.TrackEntry;
 import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
 import com.megacrit.cardcrawl.actions.GameActionManager;
 import com.megacrit.cardcrawl.actions.animations.AnimateFastAttackAction;
 import com.megacrit.cardcrawl.actions.animations.ShoutAction;
 import com.megacrit.cardcrawl.actions.animations.TalkAction;
 import com.megacrit.cardcrawl.actions.animations.VFXAction;
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.actions.utility.WaitAction;
 import com.megacrit.cardcrawl.audio.SoundMaster;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.characters.AbstractPlayer;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.Hitbox;
 import com.megacrit.cardcrawl.helpers.ScreenShake;
 import com.megacrit.cardcrawl.localization.MonsterStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
 import com.megacrit.cardcrawl.powers.MetallicizePower;
 import com.megacrit.cardcrawl.powers.StrengthPower;
 import com.megacrit.cardcrawl.powers.VulnerablePower;
 import com.megacrit.cardcrawl.powers.WeakPower;
 import com.megacrit.cardcrawl.rooms.AbstractRoom;
 import com.megacrit.cardcrawl.scenes.AbstractScene;
 import com.megacrit.cardcrawl.unlock.UnlockTracker;
 import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
 import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
 import java.util.ArrayList;

 public class BlackKnight extends AbstractMonster
         {
       public static final String ID = "BlackKnight";
       private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Champ");
       public static final String NAME = CardCrawlGame.languagePack.getEventString(ID).DESCRIPTIONS[5];
       public static final String[] MOVES = monsterStrings.MOVES;
       public static final String[] DIALOG = monsterStrings.DIALOG;
       public static final int HP = 420;
       public static final int A_9_HP = 440;
       private static final byte HEAVY_SLASH = 1;
       private static final byte DEFENSIVE_STANCE = 2;
       private static final byte EXECUTE = 3;
       private static final byte FACE_SLAP = 4;
       private static final byte GLOAT = 5; private static final byte TAUNT = 6; private static final byte ANGER = 7; private static final String STANCE_NAME = MOVES[0]; private static final String EXECUTE_NAME = MOVES[1]; private static final String SLAP_NAME = MOVES[2];
       public static final int SLASH_DMG = 16;
       public static final int EXECUTE_DMG = 10;
       public static final int SLAP_DMG = 12;
       public static final int A_2_SLASH_DMG = 18;
       public static final int A_2_SLAP_DMG = 14;
       private int slashDmg;
       private int executeDmg;
       private int slapDmg;
       private int blockAmt;
       private static final int DEBUFF_AMT = 2;
       private static final int EXEC_COUNT = 2;
       private static final int FORGE_AMT = 5;
       private static final int BLOCK_AMT = 15;
       private static final int A_9_FORGE_AMT = 6;
       private static final int A_9_BLOCK_AMT = 18; private static final int A_19_FORGE_AMT = 7; private static final int A_19_BLOCK_AMT = 20; private static final int STR_AMT = 2; private static final int A_4_STR_AMT = 3; private static final int A_19_STR_AMT = 4; private int strAmt; private int forgeAmt; private int numTurns = 0;
       private int forgeTimes = 0; private int forgeThreshold = 2;
       private boolean thresholdReached = false; private boolean firstTurn = true;
    
       public BlackKnight() {
             super(NAME, "BlackKnight", 240, 0.0F, -15.0F, 400.0F, 350.0F, null, -90.0F, 0.0F);
             this.type = EnemyType.ELITE;
             this.dialogX = (-100.0F * Settings.scale);
             this.dialogY = (10.0F * Settings.scale);
        
             loadAnimation("champResources/images/char/mainChar/blackknight.atlas", "champResources/images/char/mainChar/blackknight.json", 1.0F);
        
        
        
             AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
             e.setTime(e.getEndTime() * MathUtils.random());
             e.setTimeScale(0.8F);
        
             if (AbstractDungeon.ascensionLevel >= 9) {
                   setHp(260);
                 } else {
                   setHp(240);
                 }
        
             if (AbstractDungeon.ascensionLevel >= 19) {
                   this.slashDmg = 18;
                   this.executeDmg = 10;
                   this.slapDmg = 14;
                   this.strAmt = 4;
                   this.forgeAmt = 7;
                   this.blockAmt = 20;
                 } else if (AbstractDungeon.ascensionLevel >= 9) {
                   this.slashDmg = 18;
                   this.executeDmg = 10;
                   this.slapDmg = 14;
                   this.strAmt = 3;
                   this.forgeAmt = 6;
                   this.blockAmt = 18;
                 } else if (AbstractDungeon.ascensionLevel >= 4) {
                   this.slashDmg = 18;
                   this.executeDmg = 10;
                   this.slapDmg = 14;
                   this.strAmt = 3;
                   this.forgeAmt = 5;
                   this.blockAmt = 15;
                 } else {
                   this.slashDmg = 16;
                   this.executeDmg = 10;
                   this.slapDmg = 12;
                   this.strAmt = 2;
                   this.forgeAmt = 5;
                   this.blockAmt = 15;
                 }
        
             this.damage.add(new DamageInfo(this, this.slashDmg));
             this.damage.add(new DamageInfo(this, this.executeDmg));
             this.damage.add(new DamageInfo(this, this.slapDmg));
           }

    
       public void takeTurn()
       {
             float vfxSpeed = 0.1F;
        
             if (Settings.FAST_MODE) {
                   vfxSpeed = 0.0F;
                 }
        
             if (this.firstTurn) {
                   this.firstTurn = false;
                   if (AbstractDungeon.player.hasRelic("Champion Belt")) {
                         AbstractDungeon.actionManager.addToBottom(new TalkAction(this, DIALOG[8], 0.5F, 2.0F));
                       }
                 }
        
             switch (this.nextMove) {
                 case 7:
                       AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_CHAMP_CHARGE"));
                       AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                       AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                       AbstractDungeon.actionManager.addToBottom(new VFXAction(this, new InflameEffect(this), 0.25F));
                       AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.unique.RemoveDebuffsAction(this));
                       AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this, this, "Shackled"));
                
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.strAmt * 3), this.strAmt * 3));
                
                       break;
                 case 1:
                       AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
                       AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4F));
                       AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, false), vfxSpeed));
                
                
                
                
                
                
                       AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                 (DamageInfo)this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
                       break;
                 case 2:
                       AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(this, this, this.blockAmt));
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new MetallicizePower(this, this.forgeAmt), this.forgeAmt));
                
                       break;
                 case 3:
                       AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.animations.AnimateJumpAction(this));
                       AbstractDungeon.actionManager.addToBottom(new WaitAction(0.5F));
                
                       AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX - 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, true), vfxSpeed));
                
                
                
                
                
                
                
                       AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                 (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                
                       AbstractDungeon.actionManager.addToBottom(new VFXAction(new GoldenSlashEffect(AbstractDungeon.player.hb.cX + 60.0F * Settings.scale, AbstractDungeon.player.hb.cY, true), vfxSpeed));
                
                
                
                
                
                
                       AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                 (DamageInfo)this.damage.get(1), AbstractGameAction.AttackEffect.NONE));
                       break;
                 case 4:
                       AbstractDungeon.actionManager.addToBottom(new SFXAction("MONSTER_CHAMP_SLAP"));
                       AbstractDungeon.actionManager.addToBottom(new AnimateFastAttackAction(this));
                       AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                 (DamageInfo)this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new com.megacrit.cardcrawl.powers.FrailPower(AbstractDungeon.player, 2, true), 2));
                
                
                
                
                
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                
                
                
                
                
                       break;
                 case 5:
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.strAmt), this.strAmt));
                
                       break;
                 case 6:
                       AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CHAMP_2A", 0.3F, true));
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new WeakPower(AbstractDungeon.player, 2, true), 2));
                
                
                
                
                
                       AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                     }
        
        
        
        
        
        
             AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RollMoveAction(this));
           }
    
       public void changeState(String key)
       {
             switch (key) {
                 case "ATTACK":
                       this.state.setAnimation(0, "Attack", false);
                       this.state.addAnimation(0, "Idle", true, 0.0F);
                       break;
                 }
        
           }
    
    
       public void damage(DamageInfo info)
       {
             super.damage(info);
             if ((info.owner != null) && (info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS) && (info.output > 0)) {
                   this.state.setAnimation(0, "Hit", false);
                   this.state.addAnimation(0, "Idle", true, 0.0F);
                 }
           }
    

    

    
       protected void getMove(int num)
       {
             this.numTurns += 1;
        
             if ((this.currentHealth < this.maxHealth / 2) && (!this.thresholdReached)) {
                   this.thresholdReached = true;
                   setMove((byte)7, AbstractMonster.Intent.BUFF);
                   return;
                 }
        
             if ((!lastMove((byte)3)) && (!lastMoveBefore((byte)3)) && (this.thresholdReached)) {
                   setMove(EXECUTE_NAME, (byte)3, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(1)).base, 2, true);
                   return;
                 }
        
             if ((this.numTurns == 4) && (!this.thresholdReached)) {
                   setMove((byte)6, AbstractMonster.Intent.DEBUFF);
                   this.numTurns = 0;
                   return;
                 }
        
             if (AbstractDungeon.ascensionLevel >= 19)
                 {
                   if ((!lastMove((byte)2)) && (this.forgeTimes < this.forgeThreshold) && (num <= 30)) {
                         this.forgeTimes += 1;
                         setMove(STANCE_NAME, (byte)2, AbstractMonster.Intent.DEFEND_BUFF);
                       }
            
            
                 }
             else if ((!lastMove((byte)2)) && (this.forgeTimes < this.forgeThreshold) && (num <= 15)) {
                   this.forgeTimes += 1;
                   setMove(STANCE_NAME, (byte)2, AbstractMonster.Intent.DEFEND_BUFF);
                   return;
                 }
        
        
        
             if ((!lastMove((byte)5)) && (!lastMove((byte)2)) && (num <= 30)) {
                   setMove((byte)5, AbstractMonster.Intent.BUFF);
                   return;
                 }
        
        
             if ((!lastMove((byte)4)) && (num <= 55)) {
                   setMove(SLAP_NAME, (byte)4, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(2)).base);
                   return;
                 }
        
             if (!lastMove((byte)1)) {
                   setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
                 } else {
                   setMove(SLAP_NAME, (byte)4, AbstractMonster.Intent.ATTACK_DEBUFF, ((DamageInfo)this.damage.get(2)).base);
                 }
           }
    
    
       public void die()
       {
             useFastShakeAnimation(5.0F);
             CardCrawlGame.screenShake.rumble(4.0F);
             super.die();
             if (MathUtils.randomBoolean()) {
                   CardCrawlGame.sound.playA("VO_CHAMP_3A", 0.3F);
                 } else {
                   CardCrawlGame.sound.playA("VO_CHAMP_3B", 0.3F);
                 }
             AbstractDungeon.scene.fadeInAmbiance();
           }
     }


