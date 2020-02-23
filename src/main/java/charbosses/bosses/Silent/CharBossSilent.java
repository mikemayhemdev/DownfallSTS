package charbosses.bosses.Silent;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeIcStrike;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;

import java.util.ArrayList;

public class CharBossSilent extends AbstractCharBoss {

    public CharBossSilent() {
        super("Silent", "EvilWithin:Silent", 80, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, 0.0f, PlayerClass.THE_SILENT);
        this.energyOrb = new EnergyOrbGreen();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.9f);
    }


    @Override
    public void generateDeck() {
        ArrayList<AbstractBossDeckArchetype> archetypes = new ArrayList<AbstractBossDeckArchetype>();
        archetypes.add(new ArchetypeIcStrike());

        this.chosenArchetype = archetypes.get(AbstractDungeon.monsterRng.random(archetypes.size() - 1));
        this.chosenArchetype.initialize();
        this.chosenArchetype.simulateBuild(AbstractCharBoss.boss);
    }
}
