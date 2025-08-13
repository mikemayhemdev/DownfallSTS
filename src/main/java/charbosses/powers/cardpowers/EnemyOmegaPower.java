package charbosses.powers.cardpowers;


import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.OmegaFlashEffect;

import java.util.Iterator;

public class EnemyOmegaPower extends AbstractPower {
    public static final String POWER_ID = "OmegaPower";
    private static final PowerStrings powerStrings;

    public EnemyOmegaPower(AbstractCreature owner, int newAmount) {
        this.name = powerStrings.NAME;
        this.ID = "OmegaPower";
        this.owner = owner;
        this.amount = newAmount;
        this.updateDescription();
        this.loadRegion("omega");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
    }


    public void atEndOfTurn(boolean isPlayer) {
        this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("OmegaPower");
    }
}
