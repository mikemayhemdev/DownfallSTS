package charbosses.bosses.Silent;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeAct1PerfectedStrike;
import charbosses.bosses.Ironclad.ArchetypeAct2Strength;
import charbosses.bosses.Ironclad.ArchetypeAct3Block;
import charbosses.bosses.Ironclad.old.ArchetypeIcStrike;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;

import java.util.ArrayList;

public class CharBossSilent extends AbstractCharBoss {

    public CharBossSilent() {
        super("Silent", "EvilWithin:Silent", 80, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, -20.0f, PlayerClass.THE_SILENT);
        this.energyOrb = new EnergyOrbGreen();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.9f);
        this.energyString = "[G]";
    }


    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;
        switch (AbstractDungeon.actNum){
            case 1: archetype = new ArchetypeAct1PerfectedStrike(); break;
            case 2: archetype = new ArchetypeAct2Strength(); break;
            case 3: archetype = new ArchetypeAct3Block(); break;
            default: archetype = new ArchetypeAct3Block(); break;
        }

        archetype.initialize();
    }
}
