package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;

public class SuperSneckoSoul extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SuperSneckoSoul");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SuperSneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SuperSneckoSoul.png"));

    public SuperSneckoSoul() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void atTurnStart() {
        grayscale = false;
    }

    public void onVictory() {
        grayscale = false;
    }

    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (!grayscale && c.color != AbstractDungeon.player.getCardColor()) {
            addToTop(new GainEnergyAction(1));
            grayscale = true;
        }
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(SneckoSoul.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(SneckoSoul.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(SneckoSoul.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
