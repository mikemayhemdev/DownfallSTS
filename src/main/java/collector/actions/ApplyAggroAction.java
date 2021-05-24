package collector.actions;

import basemod.ReflectionHacks;
import collector.CollectorChar;
import collector.CollectorMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;

public class ApplyAggroAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(CollectorMod.makeID("Aggro"));
    public static final String[] TEXT = uiStrings.TEXT;

    public ApplyAggroAction() {
        actionType = ActionType.WAIT;
        duration = Settings.ACTION_DUR_FASTER;
    }

    public static String getAggroText(int amount) {
        return TEXT[0] + amount + TEXT[1];
    }

    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead() || !(AbstractDungeon.player instanceof CollectorChar) ||
                (CollectorMod.CollectorAggro == 0 && CollectorMod.TorchAggro == 0)) {
            this.isDone = true;
            return;
        }

        if (!CollectorChar.isSolo()) {
            if (CollectorMod.CollectorAggro != 0) {
                addAggroChangeEffect(AbstractDungeon.player,CollectorMod.CollectorAggro);
            }
            if (CollectorMod.TorchAggro != 0) {
                addAggroChangeEffect(((CollectorChar) AbstractDungeon.player).torch, CollectorMod.TorchAggro);
            }
            if (CollectorMod.TorchAggro - CollectorMod.CollectorAggro != 0) {
                ((CollectorChar) AbstractDungeon.player).addAggro(CollectorMod.TorchAggro - CollectorMod.CollectorAggro);
            }
        }
        CollectorMod.TorchAggro = 0;
        CollectorMod.CollectorAggro = 0;
        isDone = true;
    }

    void addAggroChangeEffect(AbstractCreature target, int amount) {
        PowerBuffEffect effect = new PowerBuffEffect(target.hb.cX - target.animX, target.hb.cY + target.hb.height / 2.0F, getAggroText(amount));
        ReflectionHacks.setPrivate(effect, PowerBuffEffect.class, "targetColor", new Color(0.7f, 0.75f, 0.7f, 1.0f));
        AbstractDungeon.effectList.add(effect);
    }
}