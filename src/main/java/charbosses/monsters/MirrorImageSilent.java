package charbosses.monsters;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.powers.FakeOrRealPower;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

import static charbosses.bosses.Silent.CharBossSilent.foggy;

public class MirrorImageSilent extends AbstractMonster {

    public static final String ID = downfallMod.makeID("MirrorImageSilent");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Silent").NAMES[0];

    public MirrorImageSilent() {
        super(NAME, ID, 1, -4.0f, -16.0f, 240.0f, 290.0f, null, -350, -25, false);
        type = EnemyType.NORMAL;
        this.loadAnimation("images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.9f);
    }

    @Override
    public void usePreBattleAction() {
        this.powers.add(new FakeOrRealPower(this));
    }

    @Override
    public void update() {
        super.update();
        if (AbstractCharBoss.boss != null && !isDead) {
            this.currentHealth = AbstractCharBoss.boss.currentHealth;
            this.maxHealth = AbstractCharBoss.boss.maxHealth;
            this.currentBlock = AbstractCharBoss.boss.currentBlock;
            this.powers = AbstractCharBoss.boss.powers;
            this.healthBarUpdatedEvent();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if (!isDead) {
            super.render(sb);
        }
    }

    @Override
    public void renderHealth(SpriteBatch sb) {
        if (!foggy) {
            super.renderHealth(sb);
        }
    }

    @Override
    public void renderPowerTips(SpriteBatch sb) {
        if (!foggy) {
            super.renderPowerTips(sb);
        }
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        if (!foggy) {
            super.renderTip(sb);
        }
    }

    /*
    @SpireOverride
    protected void renderName(SpriteBatch sb) {
        if (!foggy) {
            SpireSuper.call(sb);
        }
    }
    */

    @Override
    public void takeTurn() {

    }

    protected void getMove(int num) {
        this.setMove((byte) 0, Intent.NONE);  // This is irrelevant
    }

}
