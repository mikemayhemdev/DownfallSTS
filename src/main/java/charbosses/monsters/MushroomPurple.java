package charbosses.monsters;
import champ.powers.DrawLessNextTurnPower;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import downfall.powers.ExhaustEndOfTurnPower;

public class MushroomPurple extends AbstractMonster {
    public static final String ID = downfallMod.makeID("MushroomPurple");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    private static final int MAX_HP = 25;

    public MushroomPurple(float x, float y) {
        super(NAME, ID, MAX_HP, 0.0F, 10.0F, 120.0F, 165.0F, null, x, y);
        loadAnimation(downfallMod.assetPath("images/monsters/purpleshroom/purpleshroom.atlas"), downfallMod.assetPath("images/monsters/purpleshroom/purpleshroom.json"), 1.0F);
        state.setAnimation(0, "Idle", true);
        stateData.setMix("Idle", "Kyuuuuu", 0.2F);

    }


    @Override
    public void takeTurn() {
        state.setAnimation(0, "Kyuuuuu", false);
       // state.setTimeScale(1.6F);
        state.addAnimation(0, "Idle", true, 0.0F);
      //  state.setTimeScale(1.0F);
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ExhaustEndOfTurnPower(AbstractDungeon.player), 1));

    }

    @Override
    protected void getMove(int i) {
        setMove((byte)0, Intent.DEBUFF);

    }
}
