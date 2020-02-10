package charbosses.bosses.Defect;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeIcStrike;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;

import java.util.ArrayList;

public class CharBossDefect extends AbstractCharBoss {
    public CharBossDefect() {
        super("Defect", "EvilWithin:Defect", 75, 0.0F, -5.0F, 240.0F, 244.0F, null, 0.0f, 0.0f, AbstractPlayer.PlayerClass.DEFECT);
        this.energyOrb = new EnergyOrbBlue();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.9f);


    }

    @Override
    public void generateDeck() {
        ArrayList<AbstractBossDeckArchetype> archetypes = new ArrayList<>();
        archetypes.add(new ArchetypeIcStrike());
        //archetypes.add(new ArchetypeIcStrength());
        //archetypes.add(new ArchetypeIcRampage());
        //archetypes.add(new ArchetypeIcBlock());

        this.chosenArchetype = archetypes.get(AbstractDungeon.monsterRng.random(archetypes.size() - 1));

        this.chosenArchetype.initialize();

        this.chosenArchetype.simulateBuild(AbstractCharBoss.boss);



    }
}