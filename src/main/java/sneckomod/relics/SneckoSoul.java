package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;
import sneckomod.cards.SoulRoll;

public class SneckoSoul extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoSoul");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoSoul.png"));

    public SneckoSoul() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
        tips.add(new CardPowerTip(new SoulRoll()));
    }

    @Override
    public void atBattleStart() {
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToTop(new MakeTempCardInHandAction(new SoulRoll(), 1, false));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}