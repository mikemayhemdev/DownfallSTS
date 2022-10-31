package collector.powers;

import collector.CollectorChar;
import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SelfDestruct extends AbstractPower {
    public static final String POWER_ID = CollectorMod.makeID("SelfDestruct");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public  SelfDestruct(AbstractCreature owner) {
        this.name = NAME;
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = CollectorChar.torch;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;
        this.loadRegion("combust");
        this.description = DESCRIPTIONS[0];
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];

    }

    public void atEndOfTurn(boolean isPlayer) {
        for (AbstractMonster m: AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new DamageAction(m,new DamageInfo(owner,owner.currentHealth, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.FIRE));
        }
        addToBot(new DamageAction(owner,new DamageInfo(owner,owner.currentHealth, DamageInfo.DamageType.HP_LOSS)));
    }
}
