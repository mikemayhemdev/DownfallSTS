package charbosses.powers.cardpowers;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThousandCutsPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class EnemyThousandCutsPower extends AbstractPower {
    public static final String POWER_ID = "downfall:Enemy Thousand Cuts";
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(ThousandCutsPower.POWER_ID);
    }

    public EnemyThousandCutsPower(final AbstractCreature owner, final int amount) {
        this.name = EnemyThousandCutsPower.powerStrings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("thousandCuts");
    }

    @Override
    public void updateDescription() {
        this.description = EnemyThousandCutsPower.powerStrings.DESCRIPTIONS[0] + this.amount + EnemyThousandCutsPower.powerStrings.DESCRIPTIONS[1];
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {
        if (!(card instanceof AbstractBossCard)) {
            return;
        }
        if (Settings.FAST_MODE) {// 34
            this.addToBot(new VFXAction(new CleaveEffect()));// 35
        } else {
            this.addToBot(new VFXAction(this.owner, new CleaveEffect(), 0.2F));// 37
        }

        this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(owner, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));// 39 42
        this.flash();
    }
}
