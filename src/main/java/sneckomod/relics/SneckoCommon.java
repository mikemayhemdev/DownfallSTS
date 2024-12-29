package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.util.ColorfulPowersReward;

public class SneckoCommon extends CustomRelic {
    public static final String ID = SneckoMod.makeID("SneckoCommon");

    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SealOfApproval.png"));

    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SealOfApproval.png"));

    public SneckoCommon() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    public void onEquip() {
        (AbstractDungeon.getCurrRoom()).rewards.add(new ColorfulPowersReward());
        AbstractDungeon.combatRewardScreen.open();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
