package gremlin.patches.eventpatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.SensoryStone;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

public class SensoryStonePatch {
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:SensoryStone");
    private static final int DMG_A = 1;
    private static final int DMG_B = 2;

    @SpirePatch(clz = SensoryStone.class, method = "buttonEffect")
    public static class SensoryStoneOptionDamage
    {
        @SpireInsertPatch(
                rloc=6
        )
        public static void Insert(SensoryStone __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                __instance.imageEventText.updateDialogOption(1, strings.OPTIONS[0] + DMG_A + SensoryStone.OPTIONS[3]);
                __instance.imageEventText.updateDialogOption(2, strings.OPTIONS[1] + DMG_B + SensoryStone.OPTIONS[3]);
            }
        }
    }

    @SpirePatch(clz = SensoryStone.class, method = "buttonEffect")
    public static class SensoryStoneDamageA
    {
        @SpireInsertPatch(
                rloc=23
        )
        public static SpireReturn Insert(SensoryStone __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                AbstractDungeon.player.damage(new DamageInfo(null, DMG_A));
                ((GremlinCharacter) AbstractDungeon.player).damageGremlins(DMG_A);
                __instance.imageEventText.updateDialogOption(0, SensoryStone.OPTIONS[4]);
                __instance.imageEventText.clearRemainingOptions();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = SensoryStone.class, method = "buttonEffect")
    public static class SensoryStoneDamageB
    {
        @SpireInsertPatch(
                rloc=31
        )
        public static SpireReturn Insert(SensoryStone __instance, int buttonPressed){
            if (AbstractDungeon.player instanceof GremlinCharacter) {
                AbstractDungeon.player.damage(new DamageInfo(null, DMG_B));
                ((GremlinCharacter) AbstractDungeon.player).damageGremlins(DMG_B);
                __instance.imageEventText.updateDialogOption(0, SensoryStone.OPTIONS[4]);
                __instance.imageEventText.clearRemainingOptions();
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
