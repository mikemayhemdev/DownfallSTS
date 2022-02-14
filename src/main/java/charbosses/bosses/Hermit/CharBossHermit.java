package charbosses.bosses.Hermit;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct1SharpshooterNewAge;
import charbosses.bosses.Watcher.NewAge.ArchetypeAct1RetainNewAge;
import charbosses.bosses.Watcher.NewAge.ArchetypeAct2CalmNewAge;
import charbosses.bosses.Watcher.NewAge.ArchetypeAct3DivinityNewAge;
import charbosses.core.EnemyEnergyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;

import static hermit.HermitMod.THE_DEFAULT_SKELETON_ATLAS;
import static hermit.HermitMod.THE_DEFAULT_SKELETON_JSON;

public class CharBossHermit extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Hermit");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("hermit:hermit").NAMES[0];


    public CharBossHermit() {
        super(NAME, ID, 72, 0.0F, -5.0F, 240.0F, 270.0F, null, 0.0f, -20.0f, PlayerClass.WATCHER);
        this.energyOrb = new EnergyOrbPurple();
        this.energy = new EnemyEnergyManager(3);
        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.flipHorizontal = true;
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);
        this.energyString = "[E]";
        type = EnemyType.BOSS;

    }


    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;
        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1RetainNewAge();
            downfallMod.overrideBossDifficulty = false;
            this.currentHealth -= 100;
        } else
            switch (AbstractDungeon.actNum) {
                case 1:
                    archetype = new ArchetypeAct1SharpshooterNewAge();
                    break;
                case 2:
                    archetype = new ArchetypeAct1SharpshooterNewAge();
                    break;
                case 3:
                    archetype = new ArchetypeAct1SharpshooterNewAge();
                    break;
                default:
                    archetype = new ArchetypeAct1SharpshooterNewAge();
                    break;
            }

        archetype.initialize();
        chosenArchetype = archetype;
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }

    }


    @Override
    public void onPlayAttackCardSound() {
        switch (MathUtils.random(1)) {
            case 0:
                CardCrawlGame.sound.play("VO_GREMLINCALM_1A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_GREMLINCALM_1B");
                break;
            default:
                break;
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

        switch (MathUtils.random(1)) {
            case 0:
                CardCrawlGame.sound.play("VO_GREMLINCALM_2A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_GREMLINCALM_2B");
                break;
            default:
                break;
        }

        downfallMod.saveBossFight(CharBossHermit.ID);
    }


}
