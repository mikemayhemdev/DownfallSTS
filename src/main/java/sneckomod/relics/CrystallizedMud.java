package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

public class CrystallizedMud extends CustomRelic {

    public static final String ID = SneckoMod.makeID("CrystallizedMud");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("CrystallizedMud.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("CrystallizedMud.png"));

    //sorry mesmerizing pendant but the people hate you and champ needs a third boss relic
    public CrystallizedMud() {
        super(ID, IMG, OUTLINE, RelicTier.DEPRECATED, LandingSound.MAGICAL);
    }

    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;// 37
    }// 38

    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;// 42
    }// 43

    public void onTrigger() {this.flash();}

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
