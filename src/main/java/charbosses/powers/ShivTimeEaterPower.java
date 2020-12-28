//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import java.util.Iterator;

public class ShivTimeEaterPower extends AbstractPower {
    public static final String POWER_ID = "downfall:ShivTimeEaterPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public ShivTimeEaterPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        this.loadRegion("time");
        this.type = PowerType.BUFF;
    }

  //  public void playApplyPowerSfx() {
  //      CardCrawlGame.sound.play("POWER_TIME_WARP", 0.05F);
  //  }

    public void updateDescription() {
        this.description = DESC[0] + 1 + DESC[1];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (!(card instanceof AbstractBossCard) && (card.freeToPlayOnce || card.costForTurn <= 0)) {
            ++this.amount;
            if (this.amount == 1) {
                this.amount = 0;
                this.flashWithoutSound();
                this.playApplyPowerSfx();
               // CardCrawlGame.sound.playA("POWER_TIME_WARP", 0.25F);
               // AbstractDungeon.topLevelEffectsQueue.add(new TimeWarpTurnEndEffect());
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EnemyAccuracyPower(this.owner, 1), 1));

            }

            this.updateDescription();
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
