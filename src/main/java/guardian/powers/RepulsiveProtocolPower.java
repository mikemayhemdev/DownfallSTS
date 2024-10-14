package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;


public class RepulsiveProtocolPower extends AbstractGuardianPower implements DefensiveModeBooster {
      public static final String POWER_ID = "Guardian:RepulsiveProtocolPower";
      public static PowerType POWER_TYPE = PowerType.BUFF;
      public static String[] DESCRIPTIONS;

      public RepulsiveProtocolPower(AbstractCreature owner, int amount) {

            this.ID = POWER_ID;
            this.owner = owner;
            this.setImage("EvasiveProtocolPower84.png", "EvasiveProtocolPower32.png");
            this.type = POWER_TYPE;
            DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

            this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
            this.amount = amount;

            updateDescription();
      }

      @Override
      public void onEnter() {
            flash();
            addToBot(new ExhaustAction(this.amount, false, true, true));
      }

      @Override
      public void onLeave() {
      }

      public void updateDescription() {
            if( amount == 1){
                  this.description = DESCRIPTIONS[0];
            }else{
                  this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            }

      }
}
