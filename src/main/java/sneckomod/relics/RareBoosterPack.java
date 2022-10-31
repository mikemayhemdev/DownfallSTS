package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.UnknownRareAttack;
import sneckomod.cards.unknowns.UnknownRarePower;
import sneckomod.cards.unknowns.UnknownRareSkill;
import downfall.util.TextureLoader;

public class RareBoosterPack extends CustomRelic {

    public static final String ID = SneckoMod.makeID("RareBoosterPack");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("RareBoosterPack.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("RareBoosterPack.png"));

    public RareBoosterPack() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        float displayCount = 0.0F;
        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(new UnknownRareAttack(), (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT / 2.0F, false));// 87 89
        displayCount += (float) Settings.WIDTH / 6.0F;// 93
        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(new UnknownRareSkill(), (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT / 2.0F, false));// 87 89
        displayCount += (float) Settings.WIDTH / 6.0F;// 93
        AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(new UnknownRarePower(), (float) Settings.WIDTH / 3.0F + displayCount, (float) Settings.HEIGHT / 2.0F, false));// 87 89
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
