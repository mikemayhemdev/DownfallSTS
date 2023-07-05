package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.util.Wiz;

import java.util.ArrayList;

public class FuelCanister extends CustomRelic {
    public static final String ID = CollectorMod.makeID(FuelCanister.class.getSimpleName());
    private static final String IMG_PATH = FuelCanister.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = FuelCanister.class.getSimpleName() + ".png";

    public FuelCanister() {
        super(ID, new Texture(CollectorMod.makeRelicPath(IMG_PATH)), new Texture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        int max = -3;
        ArrayList<AbstractCard> toRetain = new ArrayList<>();
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            if (q.costForTurn > max) {
                toRetain.clear();
                toRetain.add(q);
                max = q.costForTurn;
            } else if (q.costForTurn == max) {
                toRetain.add(q);
            }
        }
        if (!toRetain.isEmpty()) {
            Wiz.getRandomItem(toRetain).retain = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

