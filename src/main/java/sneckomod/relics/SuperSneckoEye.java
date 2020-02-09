package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.SneckoEye;
import sneckomod.SneckoMod;
import theHexaghost.util.TextureLoader;

public class SuperSneckoEye extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SuperSneckoEye");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("CleanMud.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("CleanMud.png"));

    public SuperSneckoEye() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    public boolean activated = false;

    @Override
    public void atBattleStart() {
        activated = false;
        beginLongPulse();
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(SneckoEye.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(SneckoEye.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.cost == 3 && !activated) {
            flash();
            activated = true;
            card.cost = 0;// 35
            card.costForTurn = card.cost;// 36
            card.isCostModified = true;// 37
            card.freeToPlayOnce = false;// 39
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(SneckoEye.ID);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
