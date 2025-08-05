package collector.relics;

import basemod.abstracts.CustomRelic;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import downfall.util.TextureLoader;
import expansioncontent.cardmods.RetainCardMod;
import hermit.relics.Memento;
import hermit.util.Wiz;

import java.util.ArrayList;

public class FuelCanister extends CustomRelic {
    public static final String ID = CollectorMod.makeID(FuelCanister.class.getSimpleName());
    private static final String IMG_PATH = FuelCanister.class.getSimpleName() + ".png";
    private static final String OUTLINE_IMG_PATH = FuelCanister.class.getSimpleName() + ".png";

    public FuelCanister() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayerEndTurn() {
        int max = 999;
        //retains minimum now
        ArrayList<AbstractCard> toRetain = new ArrayList<>();
        for (AbstractCard q : AbstractDungeon.player.hand.group) {
            //can retain unplayable cards but not x cost cards, cannot retain ethereal cards or cards that already retain themselves
            if ((q.costForTurn < max) && !(q.costForTurn <= -1) && !(q.isEthereal) && !(q.selfRetain)) {
            //if ((q.costForTurn < max) && !(q.costForTurn < -1) && !(q.selfRetain)) {
                toRetain.clear();
                toRetain.add(q);
                max = q.costForTurn;
            } else if ((q.costForTurn == max) && !(q.costForTurn < -1) && !(q.isEthereal) && !(q.selfRetain)) {
                toRetain.add(q);
            }
        }
       //toRetain.removeIf(c -> c.isEthereal);
        if (!toRetain.isEmpty()) {
            Wiz.getRandomItem(toRetain).retain = true;
        }
    }

    public boolean canSpawn() {
        return !AbstractDungeon.player.hasRelic(RunicPyramid.ID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

