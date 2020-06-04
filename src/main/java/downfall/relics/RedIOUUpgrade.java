package downfall.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import downfall.downfallMod;

public class RedIOUUpgrade extends CustomRelic {

    public static String ID = downfallMod.makeID("RedIOUUpgrade");
    private static Texture IMG = new Texture(downfallMod.assetPath("images/relics/BanditContractUpgrade.png"));
    private static Texture OUTLINE = new Texture(downfallMod.assetPath("images/relics/Outline/BanditContractUpgrade.png"));

    public RedIOUUpgrade() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && AbstractDungeon.actNum == 3) {
            flash();
        }
    }

}
