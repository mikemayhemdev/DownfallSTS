//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Watcher.CharBossWatcher;
import charbosses.cards.AbstractBossCard;
import charbosses.stances.EnRealWrathStance;
import charbosses.stances.EnWrathStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

public class WatcherSkillPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:WatcherSkillPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;

    private boolean used = false;

    public WatcherSkillPower(AbstractCreature owner, int newAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        loadRegion("curiosity");
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
    }
    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (card instanceof AbstractBossCard) {
            return;
        }
        if (card.type.equals(AbstractCard.CardType.SKILL)) {
            this.flash();
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, this.amount), this.amount));
        }

        if (card.type.equals(AbstractCard.CardType.ATTACK)) {
            this.flash();
            this.addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner, this.amount), this.amount));
        }

        if (card.type.equals(AbstractCard.CardType.POWER)) {
            this.flash();
            this.addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, this.amount), this.amount));
            this.addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
            this.addToBot(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner, this.amount), this.amount));
        }

    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {

        if (damage > 1.0F) {
            if (this.owner instanceof AbstractCharBoss) {
                if (AbstractCharBoss.boss.stance instanceof EnWrathStance) {
                    return damage * 1.5F;
                }
                if (AbstractCharBoss.boss.stance instanceof EnRealWrathStance) {
                    return damage * 2.0F;
                }
            }
        }
        return damage;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
