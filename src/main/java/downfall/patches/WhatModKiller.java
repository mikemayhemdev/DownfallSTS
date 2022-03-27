package downfall.patches;

import basemod.patches.whatmod.WhatMod;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;

@SpirePatch(clz = WhatMod.class, method = "renderModTooltip", paramtypez = {SpriteBatch.class, Class.class, float.class, float.class})
public class WhatModKiller {
    public static SpireReturn Prefix(SpriteBatch sb, Class cla, float x, float y) {
        return SpireReturn.Return();
    }
}
