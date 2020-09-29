package champ.stances;

import champ.ChampChar;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class GladiatorStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:GladiatorStance";
    private static long sfxId = -1L;

    public GladiatorStance() {
        this.ID = STANCE_ID;// 21
        this.name = ChampChar.characterStrings.TEXT[5];
        this.updateDescription();// 23
    }// 24

    @Override
    public String getKeywordString() {
        return "champ:gladiator";
    }

    @Override
    public void updateDescription() {
        this.description = ChampChar.characterStrings.TEXT[8] + ": " + ChampChar.characterStrings.TEXT[14] + " NL " + ChampChar.characterStrings.TEXT[9] + ": " + ChampChar.characterStrings.TEXT[15];
    }

    @Override
    public void technique() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
    }

    @Override
    public void finisher() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, 1), 1));
    }
}
