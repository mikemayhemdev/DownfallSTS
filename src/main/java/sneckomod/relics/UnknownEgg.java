package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import downfall.util.TextureLoader;

import java.util.Iterator;

public class UnknownEgg extends CustomRelic {

    public static final String ID = SneckoMod.makeID("UnknownEgg");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("UnknownEgg.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("UnknownEgg.png"));

    public UnknownEgg() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public void onEquip() {
        Iterator var1 = AbstractDungeon.combatRewardScreen.rewards.iterator();// 22

        while (true) {
            RewardItem reward;
            do {
                if (!var1.hasNext()) {
                    return;// 29
                }

                reward = (RewardItem) var1.next();
            } while (reward.cards == null);// 23

            for (AbstractCard c : reward.cards) {
                this.onPreviewObtainCard(c);// 25
            }
        }
    }

    public void onPreviewObtainCard(AbstractCard c) {
        this.onObtainCard(c);// 33
    }// 34

    public void onObtainCard(AbstractCard c) {
        if (c instanceof AbstractUnknownCard && !c.upgraded) {
            c.upgrade();
        }
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;// 45
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
