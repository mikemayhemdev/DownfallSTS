package downfall.patches;


import basemod.eventUtil.EventUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;

import java.lang.reflect.Field;

@SpirePatch(
        clz = EventHelper.class,
        method = "getEventName"
)
public class EventHelperEventNamePatch {
    @SpirePostfixPatch
    static String getEventName(String __result, String key) {
        // EventHelper.getEventName has a hardcoded switch/case containing every vanilla event and its localized name
        // this patch attempts to use the NAME field of events to return the localized names for non-vanilla events.
        if(!__result.equals("")) {
            return __result;  // vanilla event name, use result
        }

        Class<? extends AbstractEvent> cls = EventUtils.getEventClass(key);
        if (cls == null) {
            return key + " (event class not found)";  // missing/changed/non-loaded event, return internal ID
        }

        try {
            Field field = cls.getDeclaredField("NAME");
            String name = (String)field.get(null);
            return name;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return key + " (NAME not found)";  // no NAME field, return internal ID
        }

    }
}
