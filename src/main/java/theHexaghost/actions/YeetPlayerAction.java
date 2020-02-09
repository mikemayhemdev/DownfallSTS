package theHexaghost.actions;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.evacipated.cardcrawl.mod.stslib.relics.OnPlayerDeathRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.vfx.combat.BlockedNumberEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;

public class YeetPlayerAction extends AbstractGameAction {
    private static final float BLOCK_ICON_X = -14.0F * Settings.scale;
    private static final float BLOCK_ICON_Y = -14.0F * Settings.scale;
    private AbstractPlayer p;


    public YeetPlayerAction() {
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        String damage = "9";

        p.currentHealth = 0;

        while (damage.length() < 10) {
            for (int i = 0; i < 10; i += MathUtils.random(1, 3)) {
                loseHP(Integer.parseInt(damage));
                loseHP(damage);
            }
            damage += "9";
        }

        boolean uGonDie = true;
        if (p.hasPotion(FairyPotion.POTION_ID) && !p.hasRelic(MarkOfTheBloom.ID)) {
            for (AbstractPotion q : p.potions) {
                if (q.ID.equals("FairyPotion")) {// 1834
                    q.flash();// 1835
                    p.currentHealth = 0;// 1836
                    q.use(p);// 1837
                    AbstractDungeon.topPanel.destroyPotion(q.slot);// 1838
                    uGonDie = false;
                    break;
                }
            }
        } else if (p.hasRelic(LizardTail.ID) && !p.hasRelic(MarkOfTheBloom.ID)) {
            p.getRelic(LizardTail.ID).onTrigger();
            uGonDie = false;
        } else {
            if (uGonDie)
                for (AbstractPower q : p.powers) {
                    if (q instanceof OnPlayerDeathPower) {
                        if (!(((OnPlayerDeathPower) q).onPlayerDeath(p, new DamageInfo(p, Integer.MAX_VALUE, DamageInfo.DamageType.HP_LOSS)))) {
                            uGonDie = false;
                            break;
                        }
                    }
                }
            if (uGonDie)
                for (AbstractRelic r : p.relics) {
                    if (r instanceof OnPlayerDeathRelic) {
                        if (!((OnPlayerDeathRelic) r).onPlayerDeath(p, new DamageInfo(p, Integer.MAX_VALUE, DamageInfo.DamageType.HP_LOSS))) {
                            uGonDie = false;
                            break;
                        }
                    }
                }
        }
        if (uGonDie) {
            p.isDead = true;
            AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
        }

        AbstractDungeon.actionManager.clearPostCombatActions();

        this.isDone = true;
    }

    private void loseHP(int damageAmount) {
        decrementBlock();

        if (damageAmount > 0) {
            p.useStaggerAnimation();

            p.currentHealth -= damageAmount;
            GameActionManager.hpLossThisCombat += damageAmount;
            AbstractDungeon.effectList.add(new StrikeEffect(p, MathUtils.random(0, Settings.WIDTH), MathUtils.random(0, Settings.HEIGHT), damageAmount));
            if (p.currentHealth < 0) {
                p.currentHealth = 0;
            }

            p.healthBarUpdatedEvent();
        }
    }

    private void loseHP(String damageAmount) {
        AbstractDungeon.effectList.add(new StrikeEffect(p, MathUtils.random(0, Settings.WIDTH), MathUtils.random(0, Settings.HEIGHT), MathUtils.randomBoolean() ? damageAmount + damageAmount + damageAmount : damageAmount + damageAmount));
    }

    private void decrementBlock() {
        if (p.currentBlock > 0) {
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
            if (Settings.SHOW_DMG_BLOCK) {
                AbstractDungeon.effectList.add(new BlockedNumberEffect(p.hb.cX, p.hb.cY + p.hb.height / 2.0F, Integer.toString(p.currentBlock)));
            }
            p.loseBlock();
            p.currentBlock = 0;

            AbstractDungeon.effectList.add(new HbBlockBrokenEffect(p.hb.cX - p.hb.width / 2.0F + BLOCK_ICON_X, p.hb.cY - p.hb.height / 2.0F + BLOCK_ICON_Y));
            CardCrawlGame.sound.play("BLOCK_BREAK");
        }
    }
}