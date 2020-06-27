package charbosses.bosses.Defect;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.anticards.Debug;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;

public class CharBossDefect extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Defect");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Defect").NAMES[0];

    public CharBossDefect() {
        super(NAME, ID, 75, 0.0F, -5.0F, 240.0F, 244.0F, null, 0.0f, -20.0f, AbstractPlayer.PlayerClass.DEFECT);
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
    public AbstractCard anticard() {
        return new Debug();
    }


    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;

        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1Streamline();
        } else
            switch (AbstractDungeon.actNum) {
                case 1:
                    archetype = new ArchetypeAct1Streamline();
                    break;
                case 2:
                    archetype = new ArchetypeAct2Claw();
                    break;
                case 3:
                    archetype = new ArchetypeAct3Orbs();
                    break;
                case 4: {
                    switch (NeowBoss.Rezzes) {
                        case 1:
                            archetype = new ArchetypeAct1Streamline();
                            break;
                        case 2:
                            archetype = new ArchetypeAct2Claw();
                            break;
                        case 3:
                            archetype = new ArchetypeAct3Orbs();
                            break;
                        default:
                            archetype = new ArchetypeAct1Streamline();
                            break;
                    }
                    break;
                }
                default:
                    archetype = new ArchetypeAct1Streamline();
                    break;
            }

        archetype.initialize();
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }
    }
}