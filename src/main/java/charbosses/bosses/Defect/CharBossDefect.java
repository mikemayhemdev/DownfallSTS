package charbosses.bosses.Defect;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.NewAge.ArchetypeAct1TurboNewAge;
import charbosses.bosses.Defect.NewAge.ArchetypeAct2ClawNewAge;
import charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge;
import charbosses.core.EnemyEnergyManager;
import charbosses.monsters.BronzeOrbWhoReallyLikesDefectForSomeReason;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;

public class CharBossDefect extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Defect");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Defect").NAMES[0];

    public int clawsPlayed = 0;

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
        type = EnemyType.BOSS;
    }


    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;

        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1TurboNewAge();
            this.currentHealth -= 100;
            downfallMod.overrideBossDifficulty = false;
        } else
            switch (AbstractDungeon.actNum) {
                case 1:
                    archetype = new ArchetypeAct1TurboNewAge();
                    break;
                case 2:
                    archetype = new ArchetypeAct2ClawNewAge();
                    break;
                case 3:
                    archetype = new ArchetypeAct3OrbsNewAge();
                    break;
                case 4: {
                    //SlimeboundMod.logger.info("Defect spawned at Archetype " + NeowBoss.Rezzes);
                    switch (NeowBoss.Rezzes) {

                        case 0:
                            archetype = new ArchetypeAct1TurboNewAge();
                            break;
                        case 1:
                            archetype = new ArchetypeAct2ClawNewAge();
                            break;
                        case 2:
                            archetype = new ArchetypeAct3OrbsNewAge();
                            break;
                        default:
                            archetype = new ArchetypeAct3OrbsNewAge();
                            break;
                    }
                    break;
                }
                default:
                    archetype = new ArchetypeAct1TurboNewAge();
                    break;
            }

        archetype.initialize();
        chosenArchetype = archetype;
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.6F);
        }

        super.damage(info);
    }

    @Override
    public void die() {
        super.die();
        downfallMod.saveBossFight(CharBossDefect.ID);

        if (hasPower(MinionPower.POWER_ID)) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m instanceof BronzeOrbWhoReallyLikesDefectForSomeReason) {
                    AbstractDungeon.actionManager.addToBottom(new InstantKillAction(m));
                }
            }
        }
    }

}