
package charbosses.monsters;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class MushroomRed extends AbstractMonster {
    public static final String ID = downfallMod.makeID("MushroomRed");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    private static final int MAX_HP = 25;
    private int protectionAmt;

    public MushroomRed(float x, float y) {
        super(NAME, ID, MAX_HP, 0.0F, 10.0F, 120.0F, 165.0F, null, x, y);
        loadAnimation(downfallMod.assetPath("images/monsters/redshroom/redshroom.atlas"), downfallMod.assetPath("images/monsters/redshroom/redshroom.json"), 1.0F);
        state.setAnimation(0, "Idle", true);
        stateData.setMix("Idle", "Rawr", 0.2F);

        this.damage.add(new DamageInfo(this, 10));
    }


    @Override
    public void takeTurn() {
        state.setAnimation(0, "Rawr", false);
       // state.setTimeScale(1.6F);
        state.addAnimation(0, "Idle", true, 0.0F);
       // state.setTimeScale(1.0F);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, damage.get(0)));

    }

    @Override
    protected void getMove(int i) {
        setMove((byte)0, Intent.ATTACK, damage.get(0).base);

    }
}