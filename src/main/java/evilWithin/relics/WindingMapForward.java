package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import evilWithin.EvilWithinMod;

public class WindingMapForward extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("WindingMapForward");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/BanditContract.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/BanditContract.png"));

    public WindingMapForward() {
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
