package hermit.patches;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import hermit.characters.hermit;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.DamageHeartEffect;

import static hermit.HermitMod.makeID;

@SpirePatch(
        clz=DamageHeartEffect.class,
        method="playSound"
)

public class DamageHeartEffectAudioPatch
{
    public static SpireReturn<Void> Prefix(DamageHeartEffect __instance)
    {
        if (AbstractDungeon.player.chosenClass == hermit.Enums.HERMIT) {
            CardCrawlGame.sound.playV(makeID("GUN1"), 1.25f); // Sound Effect

            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }
}