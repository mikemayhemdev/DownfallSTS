 package charbosses.powers.cardpowers;
 
 import com.megacrit.cardcrawl.actions.common.GainBlockAction;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.core.CardCrawlGame;
 import com.megacrit.cardcrawl.localization.PowerStrings;
 import com.megacrit.cardcrawl.powers.AbstractPower;

 public class EnemyMetallicizePower extends AbstractPower {
   public static final String POWER_ID = "Metallicize";
   private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Metallicize");
   public static final String NAME = powerStrings.NAME;
   public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
   
   public EnemyMetallicizePower(AbstractCreature owner, int armorAmt) {
     this.name = NAME;
     this.ID = "Metallicize";
     this.owner = owner;
     this.amount = armorAmt;
     updateDescription();
     loadRegion("armor");
   }
 
 
   
   public void playApplyPowerSfx() { CardCrawlGame.sound.play("POWER_METALLICIZE", 0.05F); }
 
 
   
   public void updateDescription() {
     if (this.owner.isPlayer) {
       this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
     } else {
       this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
     } 
   }



     @Override
     public void atEndOfTurn(boolean isPlayer) {
         super.atEndOfTurn(isPlayer);
         flash();
         addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
     }

//     public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
//     flash();
//     addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
//   }
 }


