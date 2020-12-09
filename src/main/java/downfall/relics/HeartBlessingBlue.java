package downfall.relics;

import basemod.abstracts.CustomRelic;
import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import downfall.downfallMod;

public class HeartBlessingBlue extends CustomRelic {

    public static final String ID = downfallMod.makeID("HeartBlessingBlue");
    private static final Texture IMG = new Texture(downfallMod.assetPath("images/relics/HeartBlessingBlue.png"));
    private static final Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/HeartBlessing.png"));

    public HeartBlessingBlue() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onEquip() {
        AbstractDungeon.player.increaseMaxHp(10, true);
    }

    @Override
    public void atBattleStartPreDraw() {
        if (AbstractDungeon.actNum == 3 && AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            if(AbstractCharBoss.boss != null) {
                if (!AbstractDungeon.player.hasRelic(HeartBlessingGreen.ID) && !AbstractDungeon.player.hasRelic(HeartBlessingRed.ID))
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(AbstractCharBoss.boss.anticard().makeCopy()));
            }
        }
    }
}
