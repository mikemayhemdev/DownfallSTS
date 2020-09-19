package champ.stances;

import champ.ChampChar;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GladiatorStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:GladiatorStance";
    private static long sfxId = -1L;

    public GladiatorStance() {
        this.ID = STANCE_ID;// 21
        this.name = ChampChar.characterStrings.TEXT[5];
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
        this.description = ChampChar.characterStrings.TEXT[8];
    }

    @Override
    public void technique() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    @Override
    public void finisher() {

    }
}
