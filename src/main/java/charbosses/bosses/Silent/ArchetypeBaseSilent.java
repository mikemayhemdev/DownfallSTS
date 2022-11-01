package charbosses.bosses.Silent;

import charbosses.bosses.AbstractBossDeckArchetype;

public class ArchetypeBaseSilent extends AbstractBossDeckArchetype {

    public ArchetypeBaseSilent(String id, String loggerName) {
        super(id, "Silent", loggerName);
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