package hermit.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;

import java.util.HashMap;

// Thank you Blank The Evil!

// Welcome to the utilities package. This package is for small utilities that make our life easier.
// You honestly don't need to bother with this unless you want to know how we're loading the textures.


public class TextureLoader {
    private static final HashMap<String, Texture> textures = new HashMap<String, Texture>();
    //public static final Logger logger = LogManager.getLogger(TextureLoader.class.getName());

    /**
     * @param textureString - String path to the texture you want to load relative to resources,
     *                      Example: "hermitResources/images/ui/missing_texture.png"
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided
     */
    public static Texture getTexture(final String textureString) {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                System.out.println("Could not find texture: " + textureString);
                return getTexture("hermitResources/images/ui/missing_texture.png");
            }
        }
        return textures.get(textureString);
    }

    /**
     * Creates an instance of the texture, applies a linear filter to it, and places it in the HashMap
     *
     * @param textureString - String path to the texture you want to load relative to resources,
     *                      Example: "img/ui/missingtexture.png"
     * @throws GdxRuntimeException
     */
    private static void loadTexture(final String textureString) throws GdxRuntimeException {
        System.out.println("HermitMod | Loading Texture: " + textureString);
        Texture texture = new Texture(textureString);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        textures.put(textureString, texture);
    }

    @SuppressWarnings("unused")
    @SpirePatch(clz = Texture.class, method = "dispose")
    public static class DisposeListener {
        @SpirePrefixPatch
        public static void DisposeListenerPatch(final Texture __instance) {
            textures.entrySet().removeIf(entry -> {
                if (entry.getValue().equals(__instance))
                    System.out.println("TextureLoader | Removing Texture: " + entry.getKey());
                return entry.getValue().equals(__instance);
            });
        }
    }

}


