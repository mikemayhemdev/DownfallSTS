package charbosses.bosses.Watcher;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;

public class CharBossWatcher extends AbstractCharBoss {

    public CharBossWatcher() {
        super("Watcher", "EvilWithin:Watcher", 72, 0.0F, -5.0F, 240.0F, 270.0F, null, 0.0f, -20.0f, PlayerClass.WATCHER);
        this.energyOrb = new EnergyOrbGreen();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/watcher/idle/skeleton.atlas", "images/characters/watcher/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.flipHorizontal = true;
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);
        this.energyString = "[P]";
    }


    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;
        switch (AbstractDungeon.actNum){
            case 1: archetype = new ArchetypeAct1Retain(); break;
            case 2: archetype = new ArchetypeAct1Retain(); break;
            case 3: archetype = new ArchetypeAct1Retain(); break;
            default: archetype = new ArchetypeAct1Retain(); break;
        }

        archetype.initialize();
    }
}
