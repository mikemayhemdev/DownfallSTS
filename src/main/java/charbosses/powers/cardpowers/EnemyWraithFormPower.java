 package charbosses.powers.cardpowers;
 
 import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
 import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
 import com.megacrit.cardcrawl.powers.DexterityPower;


 public class EnemyWraithFormPower extends AbstractPower {
   public static final String POWER_ID = "Wraith Form v2";
   private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Wraith Form v2");
   public static final String NAME = powerStrings.NAME;
   public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
   
   public EnemyWraithFormPower(AbstractCreature owner, int amount) {
     this.name = NAME;
     this.ID = "Wraith Form v2";
     this.owner = owner;
     this.amount = amount;
     updateDescription();
     loadRegion("wraithForm");
     this.canGoNegative = true;
     this.type = AbstractPower.PowerType.DEBUFF;
   }
 
 
   
   public void atEndOfTurn(boolean isPlayer) { addToBot(new ApplyPowerAction(this.owner, this.owner, new DexterityPower(this.owner, this.amount), this.amount)); }
 
 
 
 
   
   public void stackPower(int stackAmount) {
     this.fontScale = 8.0F;
     this.amount += stackAmount;
   }
 
 
   
   public void updateDescription() { this.description = DESCRIPTIONS[0] + -this.amount + DESCRIPTIONS[1]; }
 }


