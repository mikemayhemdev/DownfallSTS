package gremlin.patches.eventpatches;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.GremlinMatchGame;
import com.megacrit.cardcrawl.localization.EventStrings;
import gremlin.characters.GremlinCharacter;

import java.lang.reflect.Field;

@SpirePatch(clz = GremlinMatchGame.class, method = SpirePatch.CONSTRUCTOR)
public class MatchGameConstructorPatch
{
    private static final EventStrings strings = CardCrawlGame.languagePack.getEventString("Gremlin:MatchingGame");
    private static final String TEXT = strings.DESCRIPTIONS[2];

    public static void Postfix(GremlinMatchGame __instance) {
        if (AbstractDungeon.player instanceof GremlinCharacter) {
            setPrivateSuperInherited(__instance, GremlinMatchGame.class, "body", TEXT);
            ReflectionHacks.setPrivate(__instance, GremlinMatchGame.class, "attemptCount", 6);
        }
    }

    static void setPrivateSuperInherited(Object obj, Class<?> objClass, String fieldName, Object newValue) {
        try {
            Field targetField = objClass.getSuperclass().getSuperclass().getDeclaredField(fieldName);
            targetField.setAccessible(true);
            targetField.set(obj, newValue);
        } catch (Exception var5) {
        }

    }
}
