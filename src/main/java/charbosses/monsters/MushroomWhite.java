

package charbosses.monsters;
import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class MushroomWhite extends AbstractMonster {
    public static final String ID = downfallMod.makeID("MushroomWhite");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    private static final int MAX_HP = 25;
    private static final int PROTECTION_AMT = 10;
    private int protectionAmt;

    public MushroomWhite(float x, float y) {
        super(NAME, ID, MAX_HP, 0.0F, 10.0F, 120.0F, 165.0F, null, x, y);
        loadAnimation(downfallMod.assetPath("images/monsters/whiteshroom/whiteshroom.atlas"), downfallMod.assetPath("images/monsters/whiteshroom/whiteshroom.json"), 1.0F);
        state.setAnimation(0, "Idle", true);
        stateData.setMix("Idle", "Floop", 0.2F);
        protectionAmt = PROTECTION_AMT;
    }


    @Override
    public void takeTurn() {
        state.setAnimation(0, "Floop", false);
      //  state.setTimeScale(1.6F);
        state.addAnimation(0, "Idle", true, 0.0F);
    //    state.setTimeScale(1.0F);
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractCharBoss.boss, protectionAmt));

    }

    @Override
    protected void getMove(int i) {
        setMove((byte)0, Intent.DEFEND);

    }
}