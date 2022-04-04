package timeeater.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import timeeater.cards.alternateDimension.MagicMissile;

import static timeeater.TimeEaterMod.makeID;

public class MagicMissilePower extends AbstractTimeEaterPower {
    public static final String ID = makeID(MagicMissilePower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public MagicMissilePower(int amount) {
        super(ID, PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner,owner,this));
        super.atEndOfTurn(isPlayer);
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (card instanceof MagicMissile) return super.atDamageGive(damage + amount, type, card);
        return super.atDamageGive(damage, type, card);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
