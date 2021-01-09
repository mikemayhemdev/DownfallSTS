package sneckomod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import sneckomod.relics.BabySnecko;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "render",
        paramtypez = {SpriteBatch.class}

)
public class BabySneckoRenderPatch {
    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
        if (__instance.hasRelic(BabySnecko.ID)) {
            BabySnecko baby = (BabySnecko) __instance.getRelic(BabySnecko.ID);
            baby.renderBaby(sb);
        }

    }
}
