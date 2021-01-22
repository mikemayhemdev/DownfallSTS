
package charbosses.bosses.Crowbot;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Crowbot.NewAge.ArchetypeMBSlugNewAge;
import charbosses.core.EnemyEnergyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Bone;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import com.megacrit.cardcrawl.vfx.AwakenedEyeParticle;
import com.megacrit.cardcrawl.vfx.AwakenedWingParticle;
import downfall.downfallMod;

import java.util.ArrayList;
import java.util.Iterator;


public class CharBossCrowbot extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Crowbot");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Crowbot").NAMES[0];

    private float fireTimer = 0.0F;
    private Bone eye;
    private ArrayList<AwakenedWingParticle> wParticles = new ArrayList();

    public CharBossCrowbot() {
        super(NAME, ID, 74, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, -20.0f, Enums.Crowbot);
        this.energyOrb = new EnergyOrbBlue();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("downfallResources/images/charBoss/Crowbot/crowbot.atlas", "downfallResources/images/charBoss/Crowbot/crowbot.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.6f);
        this.energyString = "[B]";
        type = EnemyType.BOSS;
        this.eye = this.skeleton.findBone("eye");
    }


    public static class Enums {
        @SpireEnum
        public static PlayerClass Crowbot;
        @SpireEnum(name = "Crowbot")
        public static AbstractCard.CardColor Crowbot_COLOR;
        @SpireEnum(name = "Crowbot")
        public static CardLibrary.LibraryType Crowbot_BLUE;
        @SpireEnum
        public static AbstractCard.CardTags AMMO;
    }



    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype = new ArchetypeMBSlugNewAge();
        archetype.initialize();
        chosenArchetype = archetype;

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
    public void onPlayAttackCardSound() {
        switch (MathUtils.random(2)) {
            case 0:
                CardCrawlGame.sound.play("VO_Crowbot_1A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_Crowbot_1B");
                break;
            case 2:
                CardCrawlGame.sound.play("VO_Crowbot_1C");
                break;
        }
    }

    @Override
    public void die() {
        super.die();
        switch (MathUtils.random(2)) {
            case 0:
                CardCrawlGame.sound.play("VO_Crowbot_2A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_Crowbot_2B");
                break;
            case 2:
                CardCrawlGame.sound.play("VO_Crowbot_2C");
                break;
        }
    }




    @Override
    public void update() {
        super.update();
        if (!this.isDying && currentHealth <= maxHealth / 2) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.1F;
                AbstractDungeon.effectList.add(new AwakenedEyeParticle(this.skeleton
                        .getX() + this.eye.getWorldX(), this.skeleton.getY() + this.eye.getWorldY()));
                this.wParticles.add(new AwakenedWingParticle());
            }
        }

        for (Iterator<AwakenedWingParticle> p = this.wParticles.iterator(); p.hasNext(); ) {
            AwakenedWingParticle e = (AwakenedWingParticle) p.next();
            e.update();
            if (e.isDone) {
                p.remove();
            }
        }
    }
}


