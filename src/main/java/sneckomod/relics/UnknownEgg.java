package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;

import java.util.Iterator;

public class UnknownEgg extends CustomRelic {

    public static final String ID = SneckoMod.makeID("UnknownEgg");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("UnknownEgg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("UnknownEgg.png"));

    public UnknownEgg() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void onEquip() {
        Iterator<RewardItem> rewardIterator = AbstractDungeon.combatRewardScreen.rewards.iterator();

        while (rewardIterator.hasNext()) {
            RewardItem reward = rewardIterator.next();
            if (reward.cards != null) {
                for (AbstractCard c : reward.cards) {
                    this.onPreviewObtainCard(c);
                }
            }
        }
    }

    public void onPreviewObtainCard(AbstractCard c) {
        this.onObtainCard(c);
    }

    public void onObtainCard(AbstractCard c) {
        if (!c.upgraded && c.color != AbstractDungeon.player.getCardColor() || (!c.upgraded && (c instanceof AbstractUnknownCard))) {
            c.upgrade();
        }
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
