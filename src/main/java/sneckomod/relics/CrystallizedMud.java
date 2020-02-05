package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import theHexaghost.util.TextureLoader;

public class CrystallizedMud extends CustomRelic {

    public static final String ID = SneckoMod.makeID("CrystallizedMud");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoSoulOutline.png"));

    public CrystallizedMud() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;// 37
    }// 38

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;// 42
    }// 43

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
