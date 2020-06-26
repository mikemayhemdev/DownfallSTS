package charbosses.bosses.Ironclad;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeAct1Streamline;
import charbosses.cards.anticards.ShieldSmash;
import charbosses.core.EnemyEnergyManager;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;

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
        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1Streamline();
        } else
            switch (AbstractDungeon.actNum) {
                case 1:
                    archetype = new ArchetypeAct1PerfectedStrike();
                    break;
                case 2:
                    archetype = new ArchetypeAct2Strength();
                    break;
                case 3:
                    archetype = new ArchetypeAct3Block();
                    break;
                case 4: {
                    switch (NeowBoss.Rezzes) {
                        case 1:
                            archetype = new ArchetypeAct1PerfectedStrike();
                            break;
                        case 2:
                            archetype = new ArchetypeAct2Strength();
                            break;
                        case 3:
                            archetype = new ArchetypeAct3Block();
                            break;
                        default:
                            archetype = new ArchetypeAct1PerfectedStrike();
                            break;
                    }
                    break;
                }
                default:
                    archetype = new ArchetypeAct1PerfectedStrike();
                    break;
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


    @Override
    public void onPlayAttackCardSound() {

        switch (MathUtils.random(2)) {
            case 0:
                CardCrawlGame.sound.play("VO_IRONCLAD_1A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_IRONCLAD_1B");
                break;
            case 2:
                CardCrawlGame.sound.play("VO_IRONCLAD_1C");
                break;
        }
    }

    @Override
    public void die() {
        super.die();

        switch (MathUtils.random(2)) {
            case 0:
                CardCrawlGame.sound.play("VO_IRONCLAD_2A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_IRONCLAD_2B");
                break;
            case 2:
                CardCrawlGame.sound.play("VO_IRONCLAD_2C");
                break;
        }

    }
}


