 package charbosses.powers.cardpowers;
 
 import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
 import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
 import com.megacrit.cardcrawl.actions.utility.UseCardAction;
 import com.megacrit.cardcrawl.cards.AbstractCard;
 import com.megacrit.cardcrawl.cards.DamageInfo;
 import com.megacrit.cardcrawl.core.AbstractCreature;
 import com.megacrit.cardcrawl.powers.AbstractPower;
 import downfall.downfallMod;

 public class EnemyDamageMultiplierPower extends AbstractCrowbotPower implements NonStackablePower {
   public static final String POWER_ID = downfallMod.makeID("DamageMultiplier");
 
   
   public EnemyDamageMultiplierPower(AbstractCreature owner, int multi) {
     super(POWER_ID);
     
     this.owner = owner;
     this.amount = multi;
     updateDescription();
     loadRegion("doubleDamage");
     this.type = AbstractPower.PowerType.BUFF;
     this.priority = 999;
   }
 
 
 
   
   public void updateDescription() { this.description = this.DESCRIPTIONS[0] + this.amount + this.DESCRIPTIONS[1]; }

     @Override
     public void onUseCard(AbstractCard card, UseCardAction action) {
         super.onUseCard(card, action);
         if(card.type == AbstractCard.CardType.ATTACK)
         addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, ID));
     }

     public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
     if (type == DamageInfo.DamageType.NORMAL) {
       return damage * this.amount;
     }
     return damage;
   }
 }


