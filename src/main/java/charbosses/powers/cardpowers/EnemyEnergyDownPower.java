 package charbosses.powers.cardpowers;
 
 import charbosses.bosses.AbstractCharBoss;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.LocalizedStrings;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 
 public class EnemyEnergyDownPower extends AbstractPower {
   public static final String POWER_ID = "EnergyDownPower";
   private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("EnergyDownPower");
   
   public EnemyEnergyDownPower(AbstractCreature owner, int amount, boolean isFasting) {
     this.name = powerStrings.NAME;
     this.ID = "EnergyDownPower";
     this.owner = owner;
     this.amount = amount;
     updateDescription();
     this.type = AbstractPower.PowerType.DEBUFF;


     if (isFasting) {
       loadRegion("fasting");
     } else {
       loadRegion("energized_blue");
     } 

   }
 
   
   public EnemyEnergyDownPower(AbstractCreature owner, int amount) { this(owner, amount, false); }
 
 
   
   public void updateDescription() {
     StringBuilder sb = new StringBuilder();
     sb.append(powerStrings.DESCRIPTIONS[0]);
     for (int i = 0; i < this.amount; i++) {
       sb.append("[E] ");
     }
     if (powerStrings.DESCRIPTIONS[1].isEmpty()) {
       sb.append(LocalizedStrings.PERIOD);
     } else {
       sb.append(powerStrings.DESCRIPTIONS[1]);
     } 
     this.description = sb.toString();
   }

     @Override
     public void onEnergyRecharge() {
         this.flash();
         AbstractCharBoss.boss.gainEnergy(-this.amount);
     }


//   public void atStartOfTurn() {
//     addToBot(new LoseEnergyAction(this.amount));
//     flash();
//   }
 }


