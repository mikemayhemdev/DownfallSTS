package awakenedOne.powers;

import awakenedOne.cards.Psalm;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import awakenedOne.relics.StrengthBooster;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnemyHexedPower extends AbstractAwakenedPower {
    // intellij stuff buff
    public static final String NAME = EnemyHexedPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);

    public EnemyHexedPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        this.loadRegion("hex");
    }


    public float atDamageReceive(float damage, DamageInfo.DamageType type) {

        float damagereturn = 1.0F;
        if (!AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            damagereturn = (damagereturn + (0.20F * amount * damagereturn));
        }

        if (AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            damagereturn = (damagereturn + ((0.30F) * amount * damagereturn));
        }

        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * damagereturn;
        }

        return damage;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            this.flashWithoutSound();
            if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1) instanceof Psalm) {
            if (!owner.hasPower(SheerTerrorPower.POWER_ID)) {
                addToBot(new ReducePowerAction(this.owner, this.owner, this, this.amount));
            }
            }
        }
        return damageAmount;
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        int num = 20*amount;

        if(AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            num = 30*amount;
        }

        description = DESCRIPTIONS[0] + num + DESCRIPTIONS[1];
    }

}
