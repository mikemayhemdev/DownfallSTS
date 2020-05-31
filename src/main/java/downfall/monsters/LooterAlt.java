 package downfall.monsters;

 import com.badlogic.gdx.math.MathUtils;
 import com.esotericsoftware.spine.AnimationState;
 import com.esotericsoftware.spine.AnimationState.TrackEntry;
 import com.megacrit.cardcrawl.actions.GameActionManager;
 import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
 import com.megacrit.cardcrawl.actions.animations.TalkAction;
 import com.megacrit.cardcrawl.actions.common.DamageAction;
 import com.megacrit.cardcrawl.actions.common.SetMoveAction;
 import com.megacrit.cardcrawl.actions.utility.SFXAction;
 import com.megacrit.cardcrawl.audio.SoundMaster;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.core.Settings;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.helpers.Hitbox;
 import com.megacrit.cardcrawl.localization.MonsterStrings;
 import com.megacrit.cardcrawl.monsters.AbstractMonster;
 import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
 import com.megacrit.cardcrawl.random.Random;
 import downfall.downfallMod;

 import java.util.ArrayList;

 public class LooterAlt extends AbstractMonster
         {
       public static final String ID = downfallMod.makeID("LooterAlt");
       private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Looter");
       private static final MonsterStrings monsterStringsAlt = CardCrawlGame.languagePack.getMonsterStrings(downfallMod.makeID("LooterAlt"));
       public static final String NAME = monsterStrings.NAME;
       public static final String[] MOVES = monsterStrings.MOVES;
       public static final String[] DIALOG = monsterStringsAlt.DIALOG;
       private int swipeDmg;
       private int lungeDmg;
       private int escapeDef = 12;
             private int speechCount = 0;
        private static final String SLASH_MSG1 = DIALOG[0];
       private static final String DEATH_MSG1 = DIALOG[1];
       private static final String SMOKE_BOMB_MSG = DIALOG[2];
       private int slashCount = 0;

       public LooterAlt(float x, float y) {
             super(NAME, ID, 48, 0.0F, 0.0F, 200.0F, 220.0F, null, x, y);

             this.dialogX = (-30.0F * Settings.scale);
             this.dialogY = (50.0F * Settings.scale);

             if (AbstractDungeon.ascensionLevel >= 7) {
                   setHp(46, 50);
                 } else {
                   setHp(44, 48);
                 }

             if (AbstractDungeon.ascensionLevel >= 2) {
                   this.swipeDmg = 11;
                   this.lungeDmg = 14;
                 } else {
                   this.swipeDmg = 10;
                   this.lungeDmg = 12;
                 }

             this.damage.add(new DamageInfo(this, this.swipeDmg));
             this.damage.add(new DamageInfo(this, this.lungeDmg));

             loadAnimation("images/monsters/theBottom/looter/skeleton.atlas", "images/monsters/theBottom/looter/skeleton.json", 1.0F);



             AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
             e.setTime(e.getEndTime() * MathUtils.random());
           }

       public void takeTurn()
       {
             switch (this.nextMove) {
                 case 1:
                       if ((this.slashCount == 0 && this.speechCount == 0)) {
                             AbstractDungeon.actionManager.addToBottom(new TalkAction(this, SLASH_MSG1, 0.3F, 2.0F));
                           speechCount = 1;
                       }

                       playSfx();
                       AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                       AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                 (DamageInfo)this.damage.get(0)));

                       this.slashCount += 1;
                       if (this.slashCount == 2) {
                             if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {
                                   setMove((byte)2, AbstractMonster.Intent.DEFEND);
                                 } else {
                                   AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[0], (byte)4, AbstractMonster.Intent.ATTACK,
                                             ((DamageInfo)this.damage.get(1)).base));
                                 }
                           } else {
                             AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[1], (byte)1, AbstractMonster.Intent.ATTACK,
                                       ((DamageInfo)this.damage.get(0)).base));
                           }
                       break;
                 case 4:
                       playSfx();
                       this.slashCount += 1;
                       AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                       AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                                 (DamageInfo)this.damage.get(1)));
                       setMove((byte)2, AbstractMonster.Intent.DEFEND);
                       break;
                 case 2:
                       if (this.speechCount == 1) {
                           AbstractDungeon.actionManager.addToBottom(new TalkAction(this, SMOKE_BOMB_MSG, 0.75F, 2.5F));
                            this.speechCount = 2;
                       }
                       AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(this, this, this.escapeDef));
                       AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK));
                     this.slashCount = 0;
                       break;
                 }

           }

       private void playSfx()
       {
             int roll = MathUtils.random(2);
             if (roll == 0) {
                   AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1A"));
                 } else if (roll == 1) {
                   AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1B"));
                 } else {
                   AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_LOOTER_1C"));
                 }
           }

       private void playDeathSfx() {
             int roll = MathUtils.random(2);
             if (roll == 0) {
                   CardCrawlGame.sound.play("VO_LOOTER_2A");
                 } else if (roll == 1) {
                   CardCrawlGame.sound.play("VO_LOOTER_2B");
                 } else {
                   CardCrawlGame.sound.play("VO_LOOTER_2C");
                 }
           }

       public void die()
       {
             playDeathSfx();
             this.state.setTimeScale(0.1F);
             useShakeAnimation(5.0F);
             if (MathUtils.randomBoolean(0.3F)) {
                   AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.0F, DEATH_MSG1, false));
                   if (!Settings.FAST_MODE) {
                         this.deathTimer += 1.5F;
                       }
                 }
             super.die();
           }

       protected void getMove(int num)
       {
             setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
           }
     }


