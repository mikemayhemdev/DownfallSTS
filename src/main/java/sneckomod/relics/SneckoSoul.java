package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.cards.MementoCard;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;
import sneckomod.cards.SoulRoll;

public class SneckoSoul extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoSoul");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoSoul.png"));

    public SneckoSoul() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
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