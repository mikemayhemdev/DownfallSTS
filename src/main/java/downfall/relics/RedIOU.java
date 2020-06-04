package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import downfall.downfallMod;
import downfall.actions.BanditIOUAction;

public class RedIOU extends CustomRelic {

    public static String ID = downfallMod.makeID("RedIOU");
    private static Texture IMG = new Texture(downfallMod.assetPath("images/relics/BanditContract.png"));
    private static Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/BanditContract.png"));

    public RedIOU() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    @Override
    public void atBattleStartPreDraw() {
        if (!usedUp) {
            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && AbstractDungeon.actNum == 3) {
                flash();

                this.addToBot(new BanditIOUAction(AbstractDungeon.player, AbstractDungeon.getMonsters().getRandomMonster(true)));
                this.usedUp();
            }
        }

    }
}
