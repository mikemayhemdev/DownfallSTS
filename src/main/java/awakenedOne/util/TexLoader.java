package awakenedOne.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;

import java.util.HashMap;

public class TexLoader {
    private static final HashMap<String, Texture> textures = new HashMap<>();

    public static Texture getTexture(final String textureString) {

        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString, true);
            } catch (GdxRuntimeException e) {
                return getTexture("awakenedResources/images/ui/missing.png");
            }

        }
        return textures.get(textureString);
    }

    private static void loadTexture(final String textureString, boolean linearFilter) throws GdxRuntimeException {
        Texture texture = new Texture(textureString);
        if (linearFilter) {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        textures.put(textureString, texture);
    }

    @SpirePatch(clz = Texture.class, method = "dispose")
    public static class DisposeListener {
        @SpirePrefixPatch
        public static void DisposeListenerPatch(final Texture __instance) {
            textures.entrySet().removeIf(entry -> {
                if (entry.getValue().equals(__instance))
                    System.out.println("TexLoader | Removing Texture: " + entry.getKey());
                return entry.getValue().equals(__instance);
            });
        }
    }
}