package charbosses.bosses.Merchant;

import charbosses.bosses.AbstractBossDeckArchetype;

public class ArchetypeBaseMerchant extends AbstractBossDeckArchetype {

    public ArchetypeBaseMerchant(String id, String loggerName) {
        super(id, "Watcher", loggerName);

    }

    @Override
    public void initialize() {

    }
    @Override
    public void addedPreBattle() {
        //Intentionally overwritten, Merchant initializes later due to rez anim

    }
    @Override
    public void initializeBonusRelic() {

    }
}