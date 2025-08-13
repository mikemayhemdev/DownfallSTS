package charbosses.bosses.Defect;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.NewAge.ArchetypeAct1TurboNewAge;
import charbosses.bosses.Defect.NewAge.ArchetypeAct2ClawNewAge;
import charbosses.bosses.Defect.NewAge.ArchetypeAct3InserterNewAge;
import charbosses.bosses.Defect.NewAge.ArchetypeAct3OrbsNewAge;
import charbosses.bosses.Defect.Simpler.ArchetypeAct1VoidsSimple;
import charbosses.bosses.Defect.Simpler.ArchetypeAct2ClawSimple;
import charbosses.bosses.Defect.Simpler.ArchetypeAct3OrbsSimple;
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
import com.megacrit.cardcrawl.core.Settings;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;
import downfall.util.LocalizeHelper;
import expansioncontent.expansionContentMod;

public class CharBossDefect extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Defect");
    public static final String NAME = LocalizeHelper.DonwfallRunHistoryMonsterNames.TEXT[3];

    public int clawsPlayed = 0;

    public CharBossDefect() {
        super(NAME, ID, 75, 0.0F, -5.0F, 240.0F, 244.0F, null, 0.0f, 20.0f, AbstractPlayer.PlayerClass.DEFECT);
        this.energyOrb = new EnergyOrbBlue();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;

        this.dialogX = this.drawX + 0.0F * Settings.scale;
        this.dialogY = this.drawY + 170.0F * Settings.scale;
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
            if (expansionContentMod.useSimplerBosses){
                switch (AbstractDungeon.actNum) {
                    case 1:
                        archetype = new ArchetypeAct1VoidsSimple();
                        break;
                    case 2:
                        archetype = new ArchetypeAct2ClawSimple();
                        break;
                    case 3:
                        archetype = new ArchetypeAct3OrbsSimple();
                        break;
                    default:
                        archetype = new ArchetypeAct1VoidsSimple();
                        break;
                }
            } else {
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
                default:
                    archetype = new ArchetypeAct1TurboNewAge();
                    break;
            }
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

        if (hasPower(MinionPower.POWER_ID)) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (m instanceof BronzeOrbWhoReallyLikesDefectForSomeReason) {
                    AbstractDungeon.actionManager.addToBottom(new InstantKillAction(m));
                }
            }
        }
    }

}