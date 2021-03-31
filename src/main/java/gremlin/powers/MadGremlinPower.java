package gremlin.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class MadGremlinPower extends GremlinPower {
    static final String POWER_ID = getID("MadGremlin");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public MadGremlinPower(int amount) {
        super();
        this.pot = amount;
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = (strings.DESCRIPTIONS[0] + this.pot + strings.DESCRIPTIONS[1]);
    }

    @Override
    public int onAttacked(final DamageInfo info, final int damageAmount) {
        if((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) &&
                (info.owner != null) && (info.type != DamageInfo.DamageType.HP_LOSS) && (info.type != DamageInfo.DamageType.THORNS)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new StrengthPower(owner, this.pot), this.pot));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new ModifiedLoseStrengthPower(owner, this.pot), this.pot));
        }
        return damageAmount;
    }
}
