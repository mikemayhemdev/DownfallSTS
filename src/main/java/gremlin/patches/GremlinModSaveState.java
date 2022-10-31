package gremlin.patches;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import gremlin.GremlinMod;
import gremlin.characters.GremlinCharacter;

public class GremlinModSaveState implements CustomSavable<GremlinMobState> {
    @Override
    public GremlinMobState onSave() {
        if(AbstractDungeon.player instanceof GremlinCharacter) {
            GremlinMobState state = ((GremlinCharacter)(AbstractDungeon.player)).mobState;
            GremlinMod.logger.info("Saving: " + state.toString());
            return state;
        }
        return null;
    }

    @Override
    public void onLoad(GremlinMobState gremlinMobState) {
        if(AbstractDungeon.player instanceof GremlinCharacter) {
            GremlinMod.logger.info("Loading: " + gremlinMobState.toString());
            ((GremlinCharacter)(AbstractDungeon.player)).mobState = gremlinMobState;
        }
    }
}
