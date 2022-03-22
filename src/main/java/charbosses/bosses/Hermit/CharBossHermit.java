package charbosses.bosses.Hermit;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct1SharpshooterNewAge;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct3DoomsdayNewAge;
import charbosses.bosses.Watcher.NewAge.ArchetypeAct1RetainNewAge;
import charbosses.core.EnemyEnergyManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import downfall.downfallMod;

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
                    archetype = new ArchetypeAct2WheelOfFateNewAge();
                    break;
                case 3:
                    archetype = new ArchetypeAct3DoomsdayNewAge();
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

    }

    public static AbstractCard previewCard;
    public static final Vector2 PREVIEW_CARD_POS = new Vector2(Settings.WIDTH * 0.95F, (Settings.HEIGHT / 3F) * 2);

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        if (chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) {
            if (previewCard == null) {
                AbstractCard q = ((ArchetypeAct2WheelOfFateNewAge) chosenArchetype).mockDeck.get(0);
                previewCard = q.makeStatEquivalentCopy();
            }
            previewCard.current_x = previewCard.target_x = PREVIEW_CARD_POS.x;
            previewCard.current_y = previewCard.target_y = PREVIEW_CARD_POS.y;
            previewCard.drawScale = previewCard.targetDrawScale = 0.66F;
            previewCard.render(sb);
        }
    }
}
