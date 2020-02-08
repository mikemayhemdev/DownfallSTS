package eventUtil.util;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import evilWithin.patches.EvilModeCharacterSelect;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static eventUtil.EventUtils.eventLogger;

public class ConditionalEvent<T extends AbstractEvent> {
    public Class<T> eventClass;
    public Class playerClass;
    public boolean evilEvent;
    public List<String> actIDs;

    public String overrideEvent = "";

    public ConditionalEvent(Class<T> eventClass, Class playerClass, boolean evilEvent, String[] actIDs) {
        this.eventClass = eventClass;
        this.playerClass = playerClass;
        this.evilEvent = evilEvent;
        this.actIDs = Arrays.asList(actIDs);

        if (playerClass != null && !AbstractPlayer.class.isAssignableFrom(playerClass)) {
            eventLogger.info("Event " + eventClass.getSimpleName() + " was registered for a class that doesn't extend AbstractPlayer, and therefore cannot appear.");
        }
    }

    public AbstractEvent getEvent() {
        try {
            return eventClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            eventLogger.info("Failed to instantiate event " + eventClass.getName());
            e.printStackTrace();
        }
        return null;
    }

    public boolean isValid() {
        return (actIDs.isEmpty() || actIDs.contains(AbstractDungeon.id)) &&
                (!evilEvent || EvilModeCharacterSelect.evilMode) && //not an evil event, or evil event + evil mode
                (playerClass == null || AbstractDungeon.player.getClass().equals(playerClass));
    }

    @Override
    public String toString() {
        return eventClass.getSimpleName();
    }

    public String getConditions() {
        return (playerClass != null ? playerClass.getSimpleName().toUpperCase() : "ANY") + " | " + (actIDs.isEmpty() ? "ANY" : actIDs) + " | " + (evilEvent ? "EVIL" : "ANY");
    }
}
