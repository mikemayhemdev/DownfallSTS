package charbosses.bosses.Defect;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Ironclad.ArchetypeAct1PerfectedStrike;
import charbosses.bosses.Ironclad.ArchetypeAct2Strength;
import charbosses.bosses.Ironclad.ArchetypeAct3Block;
import charbosses.bosses.Ironclad.old.ArchetypeIcStrike;
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
        this.energyString = "[B]";

        this.masterMaxOrbs = 3;
        this.maxOrbs = 3;

    }

    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;

        switch (AbstractDungeon.actNum){
            case 1: archetype = new ArchetypeAct1Streamline(); break;
            case 2: archetype = new ArchetypeAct1Streamline(); break;
            case 3: archetype = new ArchetypeAct1Streamline(); break;
            default: archetype = new ArchetypeAct1Streamline(); break;
        }

        archetype.initialize();



    }
}