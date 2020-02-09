package eventUtil.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.EventHelper;
import eventUtil.EventUtils;
import eventUtil.util.ConditionalEvent;

@SpirePatch(
        clz = EventHelper.class,
        method = "getEvent"
)
public class GetEvent {
    @SpirePrefixPatch
    public static SpireReturn<AbstractEvent> prefix(String key) {
        if (EventUtils.overrideEvents.containsKey(key)) {
            for (ConditionalEvent c : EventUtils.overrideEvents.get(key)) {
                if (c.isValid() &&
                        (!EventUtils.overrideBonusConditions.containsKey(c)) ||
                        EventUtils.overrideBonusConditions.get(c).test(AbstractDungeon.player)) {
                    return SpireReturn.Return(c.getEvent());
                }
            }
        }

        if (EventUtils.normalEvents.containsKey(key)) {
            return SpireReturn.Return(EventUtils.normalEvents.get(key).getEvent());
        } else if (EventUtils.shrineEvents.containsKey(key)) {
            return SpireReturn.Return(EventUtils.shrineEvents.get(key).getEvent());
        } else if (EventUtils.oneTimeEvents.containsKey(key)) {
            return SpireReturn.Return(EventUtils.oneTimeEvents.get(key).getEvent());
        } else if (EventUtils.fullReplaceEvents.containsKey(key)) {
            return SpireReturn.Return(EventUtils.fullReplaceEvents.get(key).getEvent());
        }

        return SpireReturn.Continue();
    }
}
