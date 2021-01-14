package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import sneckomod.cards.unknowns.AbstractUnknownCard;
import downfall.util.TextureLoader;

public class SuperSneckoSoul extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SuperSneckoSoul");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SuperSneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SuperSneckoSoul.png"));

    public SuperSneckoSoul() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
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
        // Colorize the starter relic's name
        String name = new SneckoSoul().name;
        StringBuilder sb = new StringBuilder();
        for (String word : name.split(" ")) {
            sb.append("[#").append(SneckoMod.placeholderColor.toString()).append("]").append(word).append("[] ");
        }
        sb.setLength(sb.length() - 1);
        sb.append("[#").append(SneckoMod.placeholderColor.toString()).append("]");

        return DESCRIPTIONS[0] + sb.toString() + DESCRIPTIONS[1];
    }

}
