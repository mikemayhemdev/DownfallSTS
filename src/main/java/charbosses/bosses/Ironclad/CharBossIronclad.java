package charbosses.bosses.Ironclad;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.anticards.ShieldSmash;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;
import downfall.patches.ui.campfire.AddBustKeyButtonPatches;

public class CharBossIronclad extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Ironclad");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Ironclad").NAMES[0];

    public CharBossIronclad() {
        super(NAME, ID, 80, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, -20.0f, PlayerClass.IRONCLAD);
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
            case 4: {
                switch (NeowBoss.Rezzes) {
                    case 1: archetype = new ArchetypeAct1PerfectedStrike(); break;
                    case 2: archetype = new ArchetypeAct2Strength(); break;
                    case 3: archetype = new ArchetypeAct3Block(); break;
                    default: archetype = new ArchetypeAct1PerfectedStrike(); break;
                }
                break;
            }
            default: archetype = new ArchetypeAct1PerfectedStrike(); break;
        }

        archetype.initialize();
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }

        //archetypes.add(new ArchetypeIcStrike());
        //archetypes.add(new ArchetypeIcStrength());
        //archetypes.add(new ArchetypeIcRampage());
        //archetypes.add(new ArchetypeIcBlock());
        //this.chosenArchetype = archetypes.get(AbstractDungeon.monsterRng.random(archetypes.size() - 1));

        //this.chosenArchetype.simulateBuild(AbstractCharBoss.boss);
    }

    @Override
    public AbstractCard anticard() {
        return new ShieldSmash();
    }

    static {

    }
}


