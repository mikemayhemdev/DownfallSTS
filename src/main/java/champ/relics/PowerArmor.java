package champ.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import champ.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class PowerArmor extends CustomRelic {

    public static final String ID = ChampMod.makeID("PowerArmor");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("PowerArmor.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("PowerArmor.png"));

    public PowerArmor() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        BaseMod.MAX_HAND_SIZE -= 2;
    }

    public void onUnequip() {
        BaseMod.MAX_HAND_SIZE += 2;
        --AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
