package gremlin.relics;

import com.badlogic.gdx.graphics.Texture;

import basemod.abstracts.CustomRelic;
import gremlin.GremlinMod;

public abstract class AbstractGremlinRelic extends CustomRelic{
    static String getID(String ID){
        return "Gremlin:"+ID;
    }

    AbstractGremlinRelic(String id, String img, RelicTier tier, LandingSound sfx) {
        super(id, new Texture(GremlinMod.getResourcePath(img)), tier, sfx);
    }

    public void onGremlinSwap() {}

    public void onGremlinDeath() {}
}