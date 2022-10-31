package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import downfall.util.TextureLoader;

public class SneckoSoul extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoSoul");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoSoul.png"));

    public SneckoSoul() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        this.grayscale = false;
    }

    public void onVictory() {
        grayscale = false;
    }

    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (!grayscale && c.color != AbstractDungeon.player.getCardColor()) {
            addToBot(new DrawCardAction(1));
            addToBot(new GainEnergyAction(1));
            grayscale = true;
        }
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}