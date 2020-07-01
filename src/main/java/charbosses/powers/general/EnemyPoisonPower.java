//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.general;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class EnemyPoisonPower extends AbstractPower {
    public static final String POWER_ID = "Poison";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;

    public EnemyPoisonPower(AbstractCreature owner, AbstractCreature source, int poisonAmt) {
        this.name = NAME;
        this.ID = "Poison";
        this.owner = owner;
        this.source = source;
        this.amount = poisonAmt;
        if (this.amount >= 9999) {
            this.amount = 9999;
        }

        this.updateDescription();
        this.loadRegion("poison");
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }

    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            if (this.owner.hasPower(PoisonProtectionPower.POWER_ID)){
                this.addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount), AttackEffect.POISON));
            } else {
               // this.addToBot(new PoisonLoseHpAction(this.owner, this.source, this.amount, AttackEffect.POISON));
            }
        }
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            if (this.owner.hasPower(PoisonProtectionPower.POWER_ID)){
               // this.addToBot(new DamageAction(this.owner, new DamageInfo(this.owner, this.amount), AttackEffect.POISON));
            } else {
                this.addToBot(new PoisonLoseHpAction(this.owner, this.source, this.amount, AttackEffect.POISON));
            }
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Poison");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
