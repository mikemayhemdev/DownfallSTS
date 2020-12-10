package champ.stances;

import champ.ChampChar;
import champ.actions.FatigueHpLossAction;
import champ.powers.CounterPower;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class UltimateStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:UltimateStance";
    private static long sfxId = -1L;

    public UltimateStance() {
        this.ID = STANCE_ID;// 21
        this.name = ChampChar.characterStrings.TEXT[7];
        this.updateDescription();// 23
    }// 24

    @Override
    public String getKeywordString() {
        return "champ:ultimate";
    }

    public int timeLeft = 2;

    @Override
    public void onEnterStance() {
        super.onEnterStance();
        timeLeft = 2;
    }

    @Override
    public void onEndOfTurn() {
        timeLeft--;
        if (timeLeft == 0) {
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));
        }
    }

    @Override
    public void updateDescription() {
        this.description = ChampChar.characterStrings.TEXT[21];
    }

    @Override
    public void technique() {
        int x = Math.min(4, AbstractDungeon.player.currentHealth - 1);
        AbstractDungeon.actionManager.addToBottom(new FatigueHpLossAction(AbstractDungeon.player, AbstractDungeon.player, x));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(x), x));
        //AbstractDungeon.actionManager.addToBottom(new GainEnergAction(1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CounterPower(6), 6));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
                    AbstractPower q = AbstractDungeon.player.getPower(ResolvePower.POWER_ID);
                    if (q instanceof ResolvePower) {
                        ((ResolvePower) q).adjustStrength = false;
                        int x = AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount;
                        if (x > 0) {
                            addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, x));
                            addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.getPower(ResolvePower.POWER_ID)));
                        }
                    }
                }
            }
        });
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, 12));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, 1), 1));
    }
}
