package champ.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class PowerArmor extends CustomRelic {

    public static final String ID = ChampMod.makeID("PowerArmor");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PowerArmor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PowerArmor.png"));

    public static final int CAP_RESOLVE_ETC = 10;

    public PowerArmor() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        --BaseMod.MAX_HAND_SIZE;
        --BaseMod.MAX_HAND_SIZE;
    }

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
        ++BaseMod.MAX_HAND_SIZE;
        ++BaseMod.MAX_HAND_SIZE;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
