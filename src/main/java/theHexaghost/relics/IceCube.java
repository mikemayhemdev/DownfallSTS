package theHexaghost.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theHexaghost.HexaMod;
import downfall.util.TextureLoader;
import theHexaghost.powers.EnhancePower;

import static theHexaghost.HexaMod.makeRelicOutlinePath;
import static theHexaghost.HexaMod.makeRelicPath;

public class IceCube extends CustomRelic {

    //snow flake Xanatos' Icy Charm
    public static final String ID = HexaMod.makeID("IceCube");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("IceCube.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("IceCube.png"));

    public IceCube() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    //variables
    public static final int INTENSITY_LOSS = 1;

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;// 37
    }// 38

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;// 42
    }// 43

    @Override
    public void atBattleStart() {
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnhancePower(-INTENSITY_LOSS), -INTENSITY_LOSS));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + INTENSITY_LOSS + DESCRIPTIONS[1];
    }
}
