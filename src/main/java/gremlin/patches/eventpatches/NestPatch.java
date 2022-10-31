package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Nest;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import gremlin.characters.GremlinCharacter;

public class NestPatch {
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:Nest");
    private static final int DMG = 2;

    @SpirePatch(clz = Nest.class, method = "buttonEffect")
    public static class NestOptionDamage
    {
        @SpireInsertPatch(
                rloc=6
        )
        public static void Insert(Nest __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                __instance.imageEventText.updateDialogOption(1,strings.OPTIONS[0] + DMG + Nest.OPTIONS[1], new RitualDagger());
            }
        }
    }

    @SpirePatch(clz = Nest.class, method = "buttonEffect")
    public static class NestDamage
    {
        @SpireInsertPatch(
                rloc=25,
                localvars={"c"}
        )
        public static SpireReturn Insert(Nest __instance, int buttonPressed, AbstractCard c){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                AbstractDungeon.player.damage(new DamageInfo(null, DMG));
                ((GremlinCharacter) AbstractDungeon.player).damageGremlins(DMG);
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, Settings.WIDTH * 0.3F, Settings.HEIGHT / 2.0F));

                ReflectionHacks.setPrivate(__instance, Nest.class, "screenNum", 2);
                __instance.imageEventText.updateDialogOption(0, Nest.OPTIONS[4]);
                __instance.imageEventText.clearRemainingOptions();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
