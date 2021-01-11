package champ.stances;

import champ.ChampChar;
import champ.ChampMod;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class UltimateStance extends AbstractChampStance {

    public static final String STANCE_ID = "champ:UltimateStance";
    private static long sfxId = -1L;

    public UltimateStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[7];
        this.updateDescription();
    }

    @Override
    public String getKeywordString() {
        return "champ:ultimate";
    }

    @Override
    public void onEnterStance() {
        super.onEnterStance();
        ChampMod.enteredBerserkerThisTurn = true;
        ChampMod.enteredDefensiveThisTurn = true;
    }

    @Override
    public void updateDescription() {
        this.description = ChampChar.characterStrings.TEXT[21];
    }

    @Override
    public void technique() {
        ((BerserkerStance) AbstractStance.getStanceFromName(BerserkerStance.STANCE_ID)).technique();
        ((DefensiveStance) AbstractStance.getStanceFromName(DefensiveStance.STANCE_ID)).technique();
    }

    @Override
    public void finisher() {
        ((BerserkerStance) AbstractStance.getStanceFromName(BerserkerStance.STANCE_ID)).finisher();
        ((DefensiveStance) AbstractStance.getStanceFromName(DefensiveStance.STANCE_ID)).finisher();
    }
}
