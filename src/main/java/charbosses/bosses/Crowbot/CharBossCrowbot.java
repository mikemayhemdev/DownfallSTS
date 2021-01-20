package charbosses.bosses.Crowbot;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Crowbot.NewAge.ArchetypeMBSlugNewAge;
import charbosses.core.EnemyEnergyManager;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import downfall.downfallMod;


public class CharBossCrowbot extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Crowbot");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Crowbot").NAMES[0];


    public CharBossCrowbot() {
        super(NAME, ID, 80, -4.0f, -16.0f, 220.0f, 290.0f, null, 0.0f, -20.0f, Enums.Crowbot);
        this.energyOrb = new EnergyOrbBlue();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("downfallResources/images/charBoss/Crowbot/crowbot.atlas", "downfallResources/images/charBoss/Crowbot/crowbot.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.6f);
        this.energyString = "[B]";
        type = EnemyType.BOSS;
    }

    public static class Enums {
        @SpireEnum
        public static PlayerClass Crowbot;
        @SpireEnum
        public static AbstractCard.CardColor Crowbot_COLOR;
        @SpireEnum
        public static CardLibrary.LibraryType Crowbot_BLUE;
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
}


