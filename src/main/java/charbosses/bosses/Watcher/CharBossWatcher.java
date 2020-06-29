package charbosses.bosses.Watcher;

import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.ArchetypeAct1Streamline;
import charbosses.cards.anticards.PeaceOut;
import charbosses.core.EnemyEnergyManager;
import charbosses.stances.AbstractEnemyStance;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;

public class CharBossWatcher extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Watcher");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Watcher").NAMES[0];

    private Bone eyeBone;
    protected TextureAtlas eyeAtlas = null;
    protected Skeleton eyeSkeleton;
    public AnimationState eyeState;
    protected AnimationStateData eyeStateData;

    public CharBossWatcher() {
        super(NAME, ID, 72, 0.0F, -5.0F, 240.0F, 270.0F, null, 0.0f, -20.0f, PlayerClass.WATCHER);
        this.energyOrb = new EnergyOrbPurple();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/watcher/idle/skeleton.atlas", "images/characters/watcher/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.flipHorizontal = true;
        this.stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);
        this.energyString = "[P]";

        loadEyeAnimation();
        this.eyeBone = this.skeleton.findBone("eye_anchor");
    }

    private void loadEyeAnimation() {
        this.eyeAtlas = new TextureAtlas(Gdx.files.internal("images/characters/watcher/eye_anim/skeleton.atlas"));
        SkeletonJson json = new SkeletonJson(this.eyeAtlas);
        json.setScale(Settings.scale / 1.0F);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/characters/watcher/eye_anim/skeleton.json"));
        this.eyeSkeleton = new Skeleton(skeletonData);
        this.eyeSkeleton.setColor(Color.WHITE);
        this.eyeStateData = new AnimationStateData(skeletonData);
        this.eyeState = new AnimationState(this.eyeStateData);
        this.eyeStateData.setDefaultMix(0.2F);
        this.eyeState.setAnimation(0, "None", true);
    }

    public void onStanceChange(String newStance) {
        if (newStance.equals("Calm")) {
            this.eyeState.setAnimation(0, "Calm", true);
        } else if (newStance.equals("Wrath")) {
            this.eyeState.setAnimation(0, "Wrath", true);
        } else if (newStance.equals("Divinity")) {
            this.eyeState.setAnimation(0, "Divinity", true);
        } else if (newStance.equals("Neutral")) {
            this.eyeState.setAnimation(0, "None", true);
        } else {
            this.eyeState.setAnimation(0, "None", true);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        this.eyeState.update(Gdx.graphics.getDeltaTime());
        this.eyeState.apply(this.eyeSkeleton);
        this.eyeSkeleton.updateWorldTransform();
        this.eyeSkeleton.setPosition(this.skeleton.getX() + this.eyeBone.getWorldX(), this.skeleton.getY() + this.eyeBone.getWorldY());
        this.eyeSkeleton.setColor(this.tint.color);
        this.eyeSkeleton.setFlip(this.flipHorizontal, this.flipVertical);

        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, this.eyeSkeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;
        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1Streamline();
        } else
            switch (AbstractDungeon.actNum) {
                case 1:
                    archetype = new ArchetypeAct1Retain();
                    break;
                case 2:
                    archetype = new ArchetypeAct2Calm();
                    break;
                case 3:
                    archetype = new ArchetypeAct3Divinity();
                    break;
                case 4: {
                    switch (NeowBoss.Rezzes) {
                        case 1:
                            archetype = new ArchetypeAct1Retain();
                            break;
                        case 2:
                            archetype = new ArchetypeAct2Calm();
                            break;
                        case 3:
                            archetype = new ArchetypeAct3Divinity();
                            break;
                        default:
                            archetype = new ArchetypeAct3Divinity();
                            break;
                    }
                    break;
                }
                default:
                    archetype = new ArchetypeAct1Retain();
                    break;
            }

        archetype.initialize();
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }

    }

    @Override
    public AbstractCard anticard() {
        return new PeaceOut();
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
}
