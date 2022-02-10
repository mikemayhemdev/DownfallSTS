package hermit.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import hermit.characters.hermit;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.DamageHeartEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static hermit.HermitMod.makeID;

@SpirePatch(
        clz=DamageHeartEffect.class,
        method="loadImage"
)
public class DamageHeartEffectVisualPatch
{
    public static SpireReturn<TextureAtlas.AtlasRegion> Prefix(DamageHeartEffect __instance)
    {
        boolean choose_sound;

        if (AbstractDungeon.player.chosenClass == hermit.Enums.HERMIT) {

            AbstractGameAction.AttackEffect effect;
            choose_sound = MathUtils.randomBoolean();

            if (choose_sound) {
                effect = EnumPatch.HERMIT_GUN;
            }
            else
            {
                effect = EnumPatch.HERMIT_GUN2;
            }

            FlashAtkImgEffect flash = new FlashAtkImgEffect(0, 0, effect, true);
            return SpireReturn.Return(flash.img);
        }
        return SpireReturn.Continue();
    }
}