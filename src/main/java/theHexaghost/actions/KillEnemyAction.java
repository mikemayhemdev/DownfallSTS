package theHexaghost.actions;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.*;


public class KillEnemyAction extends AbstractGameAction {
    private AbstractMonster m;

    public KillEnemyAction(AbstractMonster m)
    {
        this.m = m;
    }

    @Override
    public void update() {
        int damage = MathUtils.random(2, 13);
        m.currentHealth = 0;
        while (damage < Integer.MAX_VALUE / 13) {
            loseHP(damage);
            damage *= 13;
        }

        m.die(); //(hopefully) ensure death

        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.cleanCardQueue();
            AbstractDungeon.effectList.add(new DeckPoofEffect(64.0F * Settings.scale, 64.0F * Settings.scale, true));
            AbstractDungeon.effectList.add(new DeckPoofEffect((float)Settings.WIDTH - 64.0F * Settings.scale, 64.0F * Settings.scale, false));
            AbstractDungeon.overlayMenu.hideCombatPanels();
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }
        this.isDone = true;
    }


    private void loseHP(int damageAmount)
    {
        boolean hadBlock = m.currentBlock != 0;

        boolean weakenedToZero = damageAmount == 0;
        damageAmount = decrementBlock(damageAmount);

        if (damageAmount > 0) {
            m.useStaggerAnimation();

            if (damageAmount >= 99 && !CardCrawlGame.overkill) {
                CardCrawlGame.overkill = true;
            }

            m.currentHealth -= damageAmount;
            AbstractDungeon.effectList.add(new StrikeEffect(m, m.hb.cX, m.hb.cY, damageAmount));
            if (m.currentHealth < 0) {
                m.currentHealth = 0;
            }

            m.healthBarUpdatedEvent();
        } else if (weakenedToZero && m.currentBlock == 0) {
            if (hadBlock) {
                AbstractDungeon.effectList.add(new BlockedWordEffect(m, m.hb.cX, m.hb.cY, AbstractMonster.TEXT[30]));
            } else {
                AbstractDungeon.effectList.add(new StrikeEffect(m, m.hb.cX, m.hb.cY, 0));
            }
        } else if (Settings.SHOW_DMG_BLOCK) {
            AbstractDungeon.effectList.add(new BlockedWordEffect(m, m.hb.cX, m.hb.cY, AbstractMonster.TEXT[30]));
        }
    }


    private int decrementBlock(int damageAmount)
    {
        if (m.currentBlock > 0) {
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
            if (damageAmount > m.currentBlock) {
                damageAmount -= m.currentBlock;
                if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(m.hb.cX, m.hb.cY + m.hb.height / 2.0F, Integer.toString(m.currentBlock)));
                }

                m.loseBlock();

                AbstractDungeon.effectList.add(new HbBlockBrokenEffect(m.hb.cX - m.hb.width / 2.0F + BLOCK_ICON_X, m.hb.cY - m.hb.height / 2.0F + BLOCK_ICON_Y));
                CardCrawlGame.sound.play("BLOCK_BREAK");
            } else if (damageAmount == m.currentBlock) {
                damageAmount = 0;
                m.loseBlock();
                AbstractDungeon.effectList.add(new BlockedWordEffect(m, m.hb.cX, m.hb.cY, AbstractMonster.TEXT[1]));
            } else {
                CardCrawlGame.sound.play("BLOCK_ATTACK");
                m.loseBlock(damageAmount);

                for(int i = 0; i < 18; ++i) {
                    AbstractDungeon.effectList.add(new BlockImpactLineEffect(m.hb.cX, m.hb.cY));
                }

                if (Settings.SHOW_DMG_BLOCK) {
                    AbstractDungeon.effectList.add(new BlockedNumberEffect(m.hb.cX, m.hb.cY + m.hb.height / 2.0F, Integer.toString(damageAmount)));
                }

                damageAmount = 0;
            }
        }

        return damageAmount;
    }


    private static final float BLOCK_ICON_X = -14.0F * Settings.scale;
    private static final float BLOCK_ICON_Y = -14.0F * Settings.scale;
}