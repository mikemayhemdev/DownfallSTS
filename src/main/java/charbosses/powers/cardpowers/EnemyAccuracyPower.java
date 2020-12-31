package charbosses.powers.cardpowers;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.colorless.EnShiv;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EnemyAccuracyPower extends AbstractPower {
    public static final String POWER_ID = "downfall:Enemy Accuracy";
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Accuracy");
        DESCRIPTIONS = EnemyAccuracyPower.powerStrings.DESCRIPTIONS;
    }

    public EnemyAccuracyPower(final AbstractCreature owner, final int amt) {
        this.name = EnemyAccuracyPower.powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amt;
        this.updateDescription();
        this.loadRegion("accuracy");
        this.updateExistingShivs();
    }

    @Override
    public void updateDescription() {
        this.description = EnemyAccuracyPower.DESCRIPTIONS[0] + this.amount + EnemyAccuracyPower.DESCRIPTIONS[1];
    }

    @Override
    public void stackPower(final int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.updateExistingShivs();
    }

    private void updateExistingShivs() {
        if (AbstractCharBoss.boss != null) {
            if (AbstractCharBoss.boss.hand != null) {
                for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                    if (c instanceof EnShiv) {
                        if (!c.upgraded) {
                            c.baseDamage = 4 + this.amount;
                        } else {
                            c.baseDamage = 6 + this.amount;
                        }
                    }
                }
            }
        }
        /*
        for (AbstractCard c : AbstractCharBoss.boss.drawPile.group) {
            if (c instanceof EnShiv) {
                if (!c.upgraded) {
                    c.baseDamage = 4 + this.amount;
                } else {
                    c.baseDamage = 6 + this.amount;
                }
            }
            if (c instanceof EnCloakAndDagger || c instanceof EnBladeDance) {
                c.baseDamage = 4 + this.amount;
            }
        }
        for (AbstractCard c : AbstractCharBoss.boss.discardPile.group) {
            if (c instanceof EnShiv) {
                if (!c.upgraded) {
                    c.baseDamage = 4 + this.amount;
                } else {
                    c.baseDamage = 6 + this.amount;
                }
            }
            if (c instanceof EnCloakAndDagger || c instanceof EnBladeDance) {
                c.baseDamage = 4 + this.amount;
            }
        }
        for (AbstractCard c : AbstractCharBoss.boss.exhaustPile.group) {
            if (c instanceof EnShiv) {
                if (!c.upgraded) {
                    c.baseDamage = 4 + this.amount;
                } else {
                    c.baseDamage = 6 + this.amount;
                }
            }
            if (c instanceof EnCloakAndDagger || c instanceof EnBladeDance) {
                c.baseDamage = 4 + this.amount;
            }
        }
        */
    }

    @Override
    public void onDrawOrDiscard() {
        if (AbstractCharBoss.boss != null) {
            if (AbstractCharBoss.boss.hand != null) {
                for (AbstractCard c : AbstractCharBoss.boss.hand.group) {
                    if (c instanceof EnShiv) {
                        if (!c.upgraded) {
                            c.baseDamage = 4 + this.amount;
                        } else {
                            c.baseDamage = 6 + this.amount;
                        }
                    }
                }
            }
        }
    }
}
