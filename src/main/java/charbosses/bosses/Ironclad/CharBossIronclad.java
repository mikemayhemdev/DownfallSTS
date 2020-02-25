package charbosses.bosses.Ironclad;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;

import java.util.ArrayList;

public class CharBossIronclad extends AbstractCharBoss {

    public CharBossIronclad() {
        super("Ironclad", "EvilWithin:Ironclad", 100, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, 0.0f, PlayerClass.IRONCLAD);
        this.energyOrb = new EnergyOrbRed();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/ironclad/idle/skeleton.atlas", "images/characters/ironclad/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.6f);
        this.energyString = "[R]";
    }

    @Override
    public void generateDeck() {
        ArrayList<AbstractBossDeckArchetype> archetypes = new ArrayList<AbstractBossDeckArchetype>();
        archetypes.add(new ArchetypeIcStrike());
        //archetypes.add(new ArchetypeIcStrength());
        //archetypes.add(new ArchetypeIcRampage());
        //archetypes.add(new ArchetypeIcBlock());
        this.chosenArchetype = archetypes.get(AbstractDungeon.monsterRng.random(archetypes.size() - 1));
        this.chosenArchetype.initialize();
        this.chosenArchetype.simulateBuild(AbstractCharBoss.boss);
    }

}


