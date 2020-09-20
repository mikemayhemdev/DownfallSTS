package champ.stances;

import champ.ChampChar;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CounterPower(6), 6));
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, 12));
    }
}
