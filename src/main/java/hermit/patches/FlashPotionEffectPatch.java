package hermit.patches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.DamageHeartEffect;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;
import hermit.characters.hermit;
import hermit.potions.Eclipse;
import hermit.util.TextureLoader;
import javassist.CtBehavior;

import java.util.regex.Matcher;

import static hermit.HermitMod.makeID;

@SpirePatch(clz = FlashPotionEffect.class, method = SpirePatch.CONSTRUCTOR)

public class FlashPotionEffectPatch {

    public static void Postfix(FlashPotionEffect __instance, AbstractPotion p)
    {
        if (p instanceof Eclipse)
        {
            ReflectionHacks.setPrivate(__instance, FlashPotionEffect.class, "containerImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_glass.png"));
            ReflectionHacks.setPrivate(__instance, FlashPotionEffect.class, "liquidImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_liquid.png"));
            ReflectionHacks.setPrivate(__instance, FlashPotionEffect.class, "hybridImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_hybrid.png"));
            ReflectionHacks.setPrivate(__instance, FlashPotionEffect.class, "spotsImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_spots.png"));
        }
    }
}