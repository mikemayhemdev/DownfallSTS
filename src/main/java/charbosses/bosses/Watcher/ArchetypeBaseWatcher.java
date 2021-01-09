package charbosses.bosses.Watcher;

import charbosses.bosses.AbstractBossDeckArchetype;

public class ArchetypeBaseWatcher extends AbstractBossDeckArchetype {

    public ArchetypeBaseWatcher(String id, String loggerName) {
        super(id, "Watcher", loggerName);

    }

    @Override
    public void initialize() {

    }

    @Override
    public void addedPreBattle() {
        super.addedPreBattle();
    }

    @Override
    public void initializeBonusRelic() {

    }
}