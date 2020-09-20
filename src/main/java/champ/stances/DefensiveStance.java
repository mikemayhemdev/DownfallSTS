package champ.stances;

import champ.ChampChar;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DefensiveStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:DefensiveStance";
    private static long sfxId = -1L;

    public DefensiveStance() {
        this.ID = STANCE_ID;// 21
        this.name = ChampChar.characterStrings.TEXT[4];
        this.updateDescription();// 23
    }// 24

    @Override
    public void onEnterStance() {
        super.onEnterStance();
    }

    @Override
    public void onExitStance() {
        super.onExitStance();
    }

    @Override
    public void updateDescription() {
        this.description = ChampChar.characterStrings.TEXT[7];
    }

    @Override
    public void technique() {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 4));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ResolvePower(4), 4));
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (AbstractDungeon.player.hasPower(ResolvePower.POWER_ID)) {
                    int x = AbstractDungeon.player.getPower(ResolvePower.POWER_ID).amount;
                    if (x > 0) {
                        addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, x));
                        addToTop(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.getPower(ResolvePower.POWER_ID)));
                    }
                }
            }
        });
    }
}
