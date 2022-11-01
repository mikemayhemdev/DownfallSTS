package gremlin.patches;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.downfallMod;
import gremlin.GremlinMod;
import gremlin.characters.GremlinCharacter;

public class GremlinModSaveState implements CustomSavable<GremlinMobState> {
    @Override
    public GremlinMobState onSave() {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.GREMLIN)) {
            GremlinMobState state = ((GremlinCharacter) (AbstractDungeon.player)).mobState;
            GremlinMod.logger.info("Saving: " + state.toString());
            return state;
        }
        return null;
    }

    @Override
    public void onLoad(GremlinMobState gremlinMobState) {
        if (AbstractDungeon.player.chosenClass.equals(downfallMod.Enums.GREMLIN)) {
            GremlinMod.logger.info("Loading: " + gremlinMobState.toString());
            ((GremlinCharacter) (AbstractDungeon.player)).mobState = gremlinMobState;
        }
    }
}
