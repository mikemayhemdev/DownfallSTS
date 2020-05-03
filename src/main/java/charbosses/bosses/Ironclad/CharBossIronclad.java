package charbosses.bosses.Ironclad;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;
import evilWithin.patches.ui.campfire.AddBustKeyButtonPatches;

import java.util.ArrayList;

public class CharBossIronclad extends AbstractCharBoss {

    public CharBossIronclad() {
        super("Ironclad", "EvilWithin:Ironclad", 80, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, -20.0f, PlayerClass.IRONCLAD);
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
        //ArrayList<AbstractBossDeckArchetype> archetypes = new ArrayList<AbstractBossDeckArchetype>();
        AbstractBossDeckArchetype archetype;
        switch (AbstractDungeon.actNum){
            case 1: archetype = new ArchetypeAct1PerfectedStrike(); break;
            case 2: archetype = new ArchetypeAct2Strength(); break;
            case 3: archetype = new ArchetypeAct3Block(); break;
            default: archetype = new ArchetypeAct1PerfectedStrike(); break;
        }

        archetype.initialize();
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }
        if (AbstractDungeon.actNum == 3) {
            if (AddBustKeyButtonPatches.KeyFields.bustedSapphire.get(AbstractDungeon.player)) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(archetype.anticard().makeCopy()));
            }
            if (AddBustKeyButtonPatches.KeyFields.bustedRuby.get(AbstractDungeon.player)) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(archetype.anticard().makeCopy()));
            }
            if (AddBustKeyButtonPatches.KeyFields.bustedEmerald.get(AbstractDungeon.player)) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(archetype.anticard().makeCopy()));
            }
        }

        //archetypes.add(new ArchetypeIcStrike());
        //archetypes.add(new ArchetypeIcStrength());
        //archetypes.add(new ArchetypeIcRampage());
        //archetypes.add(new ArchetypeIcBlock());
        //this.chosenArchetype = archetypes.get(AbstractDungeon.monsterRng.random(archetypes.size() - 1));

        //this.chosenArchetype.simulateBuild(AbstractCharBoss.boss);
    }

}


