package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.util.TextureLoader;
import guardian.rewards.GemRewardButRelicRng;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.UnknownClass;
import sneckomod.util.ColorfulPowersReward;

import java.util.ArrayList;

import static sneckomod.util.ColorfulCardReward.TEXT;

public class SneckoCommon extends CustomRelic {
    public static final String ID = SneckoMod.makeID("SneckoCommon");

    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SealOfApproval.png"));

    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SealOfApproval.png"));

    public SneckoCommon() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.COMMON, AbstractRelic.LandingSound.MAGICAL);
    }

    public void onEquip() {
        AbstractDungeon.getCurrRoom().rewards.add(new ColorfulPowersReward());
        AbstractDungeon.combatRewardScreen.open(this.DESCRIPTIONS[1]);
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
        AbstractDungeon.combatRewardScreen.rewards.remove(AbstractDungeon.combatRewardScreen.rewards.size()-1);
        }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
}
