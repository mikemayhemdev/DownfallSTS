package timeeater.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static timeeater.TimeEaterMod.makeID;

public class HastePower extends AbstractTimeEaterPower {
    public static final String ID = makeID(HastePower.class.getSimpleName());
    public static final PowerStrings strs = CardCrawlGame.languagePack.getPowerStrings(ID);

    public HastePower(int amount) {
        super(ID, strs.NAME, PowerType.BUFF, false, AbstractDungeon.player, amount);
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        AbstractDungeon.player.gainEnergy(1);
        addToBot(new DrawCardAction(1));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public void updateDescription() {
        description = (amount == 1 ? DESCRIPTIONS[0] : (DESCRIPTIONS[1] + amount + DESCRIPTIONS[2]));
    }
}
