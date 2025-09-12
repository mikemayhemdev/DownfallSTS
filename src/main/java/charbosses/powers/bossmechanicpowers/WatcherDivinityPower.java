//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.CharBossWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.powers.cardpowers.EnemyAccuracyPower;
import charbosses.powers.cardpowers.EnemyMantraPower;
import charbosses.stances.EnWrathStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import com.megacrit.cardcrawl.vfx.combat.DevotionEffect;
import com.megacrit.cardcrawl.vfx.combat.TimeWarpTurnEndEffect;
import downfall.downfallMod;
import hermit.util.Wiz;

public class WatcherDivinityPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherDivinityPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    private boolean triggeredTheThing;

    public WatcherDivinityPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESC[0];
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (downfallMod.useLegacyBosses) {
            if (!(card instanceof AbstractBossCard)) {
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EnemyMantraPower(this.owner, 1), 1));
            }
        }



        if (!downfallMod.useLegacyBosses) {
        if (!(card instanceof AbstractBossCard) && !card.purgeOnUse) {
            if (this.amount < 6) {
                flash();
                this.amount++;
                if (this.amount == 6) {
                    Wiz.atb(new VFXAction(new DevotionEffect(), 0.4F));
                    this.addToBot(new ApplyPowerAction(this.owner, this.owner, new IntangiblePlayerPower(this.owner, 1), 1));
                    this.addToBot(new ApplyPowerAction(this.owner, this.owner, new EnemyMantraPower(this.owner, 1), 1));
                }
            }
        }
        }
    }

    @Override
    public void atStartOfTurn() {
        this.amount = 0;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        this.amount = 0;
        updateDescription();
    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
