package theHexaghost.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;

public class PlayCardAndExhaustPower extends AbstractPower {
      public static final String POWER_ID = HexaMod.makeID("PlayCardAndExhaustPower");
      private static final PowerStrings powerStrings;
      public static final String NAME;
      public static final String[] DESCRIPTIONS;

      public PlayCardAndExhaustPower(int amount) {
            this.name = NAME;
            this.ID = POWER_ID;
            this.owner = AbstractDungeon.player;
            this.amount = amount;
            this.updateDescription();
            this.loadRegion("amplify");
      }

      public void reducePower(int reduceAmount) {
            this.fontScale = 8.0F;
            this.amount -= reduceAmount;
            if (this.amount <= 0) {
                  this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
      }

      public void updateDescription() {
            if (this.amount == 1) {
                  this.description = DESCRIPTIONS[0];
            } else {
                  this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
            }

      }

      public void onUseCard(AbstractCard card, UseCardAction action) { // free to play part is at FreePlayPatch.java
            if (this.amount > 0) {
                  this.flash();
                  action.exhaustCard = true;
                  this.reducePower(1);
            }
      }

      static {
            powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
            NAME = powerStrings.NAME;
            DESCRIPTIONS = powerStrings.DESCRIPTIONS;
      }
}
