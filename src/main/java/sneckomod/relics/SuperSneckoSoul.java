package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import sneckomod.actions.SuperSneckoSoulAction;
import downfall.util.TextureLoader;
import sneckomod.cards.SoulRoll;

public class SuperSneckoSoul extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SuperSneckoSoul");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SuperSneckoSoul.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SuperSneckoSoul.png"));

    public SuperSneckoSoul() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        this.counter = 0;
        tips.add(new CardPowerTip(new SoulRoll()));
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void onVictory() {
        this.counter = 0;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (this.counter == 2) {
            this.counter = 0;
        }
        if (this.counter == 0) {
            this.addToTop(new MakeTempCardInHandAction(new SoulRoll(), 1, false));
            flash();
        }
        if (this.counter != 0) {
            AbstractDungeon.actionManager.addToBottom(new SuperSneckoSoulAction());
        }

        ++this.counter;
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
