package charbosses.bosses.Silent;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeAct1Streamline;
import charbosses.cards.anticards.Antidote;
import charbosses.core.EnemyEnergyManager;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;
import slimebound.SlimeboundMod;

public class CharBossSilent extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Silent");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Silent").NAMES[0];

    public CharBossSilent() {
        super(NAME, ID, 80, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, -20.0f, PlayerClass.THE_SILENT);
        this.energyOrb = new EnergyOrbGreen();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.9f);
        this.energyString = "[G]";
        type = EnemyType.BOSS;
    }

    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;
        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1Streamline();
        } else
            switch (AbstractDungeon.actNum) {
                case 1:
                    archetype = new ArchetypeAct1Shivs();
                    break;
                case 2:
                    archetype = new ArchetypeAct2Finisher();
                    break;
                case 3:
                    archetype = new ArchetypeAct3Poison();
                    break;
                case 4:
                    SlimeboundMod.logger.info("Silent spawned at Archetype " + NeowBoss.Rezzes);
                    {
                    switch (NeowBoss.Rezzes) {
                        case 1:
                            archetype = new ArchetypeAct1Shivs();
                            break;
                        case 2:
                            archetype = new ArchetypeAct2Finisher();
                            break;
                        case 3:
                            archetype = new ArchetypeAct3Poison();
                            break;
                        default:
                            archetype = new ArchetypeAct3Poison();
                            break;
                    }
                    break;
                }
                default:
                    archetype = new ArchetypeAct1Shivs();
                    break;
            }

        archetype.initialize();
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }
    }

    @Override
    public AbstractCard anticard() {
        return new Antidote();
    }

    @Override
    public void onPlayAttackCardSound() {
        switch (MathUtils.random(1)) {
            case 0:
                CardCrawlGame.sound.play("VO_SILENT_1A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_SILENT_1B");
                break;
            default:
                break;
        }
    }


    @Override
    public void die() {
        super.die();

        switch (MathUtils.random(1)) {
            case 0:
                CardCrawlGame.sound.play("VO_SILENT_2A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_SILENT_2B");
                break;
            default:
                break;
        }

        downfallMod.saveBossFight("downfall:CharBossSilent");
    }

}
