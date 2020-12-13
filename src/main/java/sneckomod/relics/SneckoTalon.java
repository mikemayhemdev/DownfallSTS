package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.SneckoMod;
import theHexaghost.util.TextureLoader;

import java.util.ArrayList;

public class SneckoTalon extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoTalon");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoTalon.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoTalon.png"));

    public SneckoTalon() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStartPostDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = 0;
                for (AbstractCard q : AbstractDungeon.player.hand.group) {
                    if (q.cost > x)
                        x = q.cost;
                }
                ArrayList<AbstractCard> possCardsList = new ArrayList<>();
                for (AbstractCard q : AbstractDungeon.player.hand.group) {
                    if (q.cost == x)
                        possCardsList.add(q);
                }
                if (!possCardsList.isEmpty()) {
                    flash();
                    AbstractCard q = possCardsList.get(AbstractDungeon.cardRandomRng.random(possCardsList.size() - 1));
                    q.costForTurn = q.cost - 1;
                    q.isCostModifiedForTurn = true;
                  //  q.isCostModified = true;
                    q.superFlash();
                }
            }
        });
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
