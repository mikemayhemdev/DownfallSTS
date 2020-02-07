package eventUtil;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.events.AbstractEvent;
import eventUtil.util.ConditionalEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class EventUtils {
    /* D O C U M E N T A T I O N

        Register your events with EventUtils.registerEvent instead of BaseMod.addEvent

        Supply whatever arguments you need to make the event spawn how you want

        ID:
            The event ID. Make it whatever you want.
        eventClass:
            The class of the event itself.
        playerClass:
            The class for an AbstractPlayer this event can ONLY spawn for. If you want more specific control, use the bonusCondition.
            Default: null
        actIDs:
            The act IDs this event can spawn in.
            Default: null
        evilEvent:
            This event can only appear in evil mode.
            Default: false
        overrideEvent:
            The ID of an event to replace. There are two modes of replacement: Override and Full Replace.
            Override will conditionally replace the event when it is encountered. If the conditions fail, the normal event will occur.
            Full Replace will perform its check when the dungeon is instantiated, completely replacing the original event. The original event cannot appear.
                For Full Replace, the bonus condition is checked to determine if the event can appear, not when the dungeon is instantiated.

            Use certain constructors or the type parameter to determine the override method.

            Default: null
        bonusCondition:
            A predicate that determines if the event can be triggered. This is checked whenever an event is encountered.
            Default: null
        type:
            The event type. Determines what pool it is (or the type of replacement, if overriding)
            Default: EventType.NORMAL

     */
    public static Logger eventLogger = LogManager.getLogger("EventUtil");

    private static int id = 0;

    public static HashMap<String, Predicate<AbstractPlayer>> normalEventBonusConditions = new HashMap<>();
    public static HashMap<String, Predicate<AbstractPlayer>> specialEventBonusConditions = new HashMap<>();
    public static HashMap<ConditionalEvent, Predicate<AbstractPlayer>> overrideBonusConditions = new HashMap<>();

    public static HashMap<String, ConditionalEvent> normalEvents = new HashMap<>();
    public static HashMap<String, ConditionalEvent> shrineEvents = new HashMap<>();
    public static HashMap<String, ConditionalEvent> oneTimeEvents = new HashMap<>();
    public static HashMap<String, ConditionalEvent> fullReplaceEvents = new HashMap<>(); //to actually get the replacement events, after replacement occurs

    public static HashMap<String, ArrayList<ConditionalEvent>> overrideEvents = new HashMap<>();
    public static HashMap<String, ArrayList<ConditionalEvent>> fullReplaceEventList = new HashMap<>();

    public enum EventType
    {
        NORMAL,
        SHRINE,
        ONE_TIME,
        OVERRIDE,
        FULL_REPLACE
    }


    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, Class playerClass)
    {
        registerEvent(ID, eventClass, playerClass, null, false, null, null, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, String[] actIDs)
    {
        registerEvent(ID, eventClass, null, actIDs, false, null, null, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, boolean evilEvent)
    {
        registerEvent(ID, eventClass, null, null, evilEvent, null, null, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, boolean evilEvent, String overrideEvent)
    {
        registerEvent(ID, eventClass, null, null, evilEvent, overrideEvent, null, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, Class playerClass, String[] actIDs)
    {
        registerEvent(ID, eventClass, playerClass, actIDs, false, null, null, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, Class playerClass, String actID)
    {
        registerEvent(ID, eventClass, playerClass, new String[] { actID }, false, null, null, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, boolean evilEvent, String[] actIDs)
    {
        registerEvent(ID, eventClass, null, actIDs, evilEvent, null, null, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, String[] actIDs, Predicate<AbstractPlayer> bonusCondition)
    {
        registerEvent(ID, eventClass, null, actIDs, false, null, bonusCondition, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, Class playerClass, String[] actIDs, Predicate<AbstractPlayer> bonusCondition)
    {
        registerEvent(ID, eventClass, playerClass, actIDs, false, null, bonusCondition, EventType.NORMAL);
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, Class playerClass, String overrideEvent, boolean fullReplace)
    {
        registerEvent(ID, eventClass, playerClass, null, false, overrideEvent, null, fullReplace ? EventType.FULL_REPLACE : (overrideEvent == null ? EventType.NORMAL : EventType.OVERRIDE));
    }
    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, Class playerClass, String overrideEvent, Predicate<AbstractPlayer> bonusCondition, EventType type)
    {
        registerEvent(ID, eventClass, playerClass, null, false, overrideEvent, bonusCondition, type);
    }


    public static<T extends AbstractEvent> void registerEvent(String ID, Class<T> eventClass, Class playerClass, String[] actIDs, boolean evilEvent, String overrideEvent, Predicate<AbstractPlayer> bonusCondition, EventType type)
    {
        if (!(overrideEvent != null || evilEvent || actIDs != null || playerClass != null || bonusCondition != null))
        {
            eventLogger.info("Event " + eventClass.getName() + " has no special conditions, and should be registered through BaseMod instead.");
            return;
        }

        ConditionalEvent<T> c = new ConditionalEvent<T>(eventClass,
                playerClass,
                evilEvent,
                actIDs == null ? new String[] { } : actIDs);

        if (type == EventType.OVERRIDE && overrideEvent != null)
        {
            c.overrideEvent = overrideEvent;

            if (!overrideEvents.containsKey(overrideEvent))
            {
                overrideEvents.put(overrideEvent, new ArrayList<>());
            }
            overrideEvents.get(overrideEvent).add(c);

            eventLogger.info("Override event " + c + " for event " + overrideEvent + " registered. " + c.getConditions());

            if (bonusCondition != null)
            {
                overrideBonusConditions.put(c, bonusCondition);
                eventLogger.info("  This event has a bonus condition.");
            }
        }
        else if (type == EventType.FULL_REPLACE && overrideEvent != null)
        {
            String key = ID;
            if (fullReplaceEvents.containsKey(key))
            {
                key = generateEventKey(ID);
            }

            c.overrideEvent = key;

            if (!fullReplaceEventList.containsKey(overrideEvent))
            {
                fullReplaceEventList.put(overrideEvent, new ArrayList<>());
            }
            fullReplaceEventList.get(overrideEvent).add(c);

            fullReplaceEvents.put(key, c);

            eventLogger.info("Full Replacement event " + c + " for event " + overrideEvent + " registered. " + c.getConditions());
            if (bonusCondition != null)
            {
                normalEventBonusConditions.put(key, bonusCondition);
                specialEventBonusConditions.put(key, bonusCondition);
                eventLogger.info("  This event has a bonus condition.");
            }
        }
        else if (type == EventType.ONE_TIME)
        {
            String key = ID;
            if (oneTimeEvents.containsKey(key))
            {
                key = generateEventKey(ID);
            }

            oneTimeEvents.put(key, c);

            eventLogger.info("SpecialOneTimeEvent " + c + " registered. " + c.getConditions());
            if (bonusCondition != null)
            {
                specialEventBonusConditions.put(key, bonusCondition);
                eventLogger.info("  This event has a bonus condition.");
            }
        }
        else if (type == EventType.SHRINE)
        {
            String key = ID;
            if (shrineEvents.containsKey(key))
            {
                key = generateEventKey(ID);
            }

            shrineEvents.put(key, c);

            eventLogger.info("Shrine " + c + " registered. " + c.getConditions());
            if (bonusCondition != null)
            {
                specialEventBonusConditions.put(key, bonusCondition);
                eventLogger.info("  This event has a bonus condition.");
            }
        }
        else
        {
            String key = ID;
            if (normalEvents.containsKey(key))
            {
                key = generateEventKey(ID);
            }

            normalEvents.put(key, c);

            eventLogger.info("Event " + c + " registered. " + c.getConditions());
            if (bonusCondition != null)
            {
                normalEventBonusConditions.put(key, bonusCondition);
                eventLogger.info("  This event has a bonus condition.");
            }
        }
    }

    private static<T extends AbstractEvent> String generateEventKey(String ID)
    {
        return id++ + ":" + ID;
    }
}
