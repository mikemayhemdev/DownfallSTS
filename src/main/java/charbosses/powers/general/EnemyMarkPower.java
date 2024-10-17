package charbosses.powers.general;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyMarkPower extends AbstractPower {
      public static final String POWER_ID = "PathToVictoryPower";
      private static final PowerStrings powerStrings;
      private AbstractCreature source;
      public EnemyMarkPower(AbstractCreature owner, AbstractCreature source, int amt) {
            this.name = powerStrings.NAME;
            this.ID = "PathToVictoryPower";
            this.owner = owner;
            this.amount = amt;
            this.source = source;
            this.type = PowerType.DEBUFF;
            this.updateDescription();
            this.loadRegion("pressure_points");
      }
      public void updateDescription() {
            this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
      }

      static {
            powerStrings = CardCrawlGame.languagePack.getPowerStrings("PathToVictoryPower");
      }
}