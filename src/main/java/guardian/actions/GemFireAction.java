//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

public class GemFireAction extends AbstractGameAction {
    private DamageInfo info;
    private float startingDuration;

    public GemFireAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.WAIT;
        this.attackEffect = AttackEffect.FIRE;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
    }

    public void update() {
        int hitCount = 0;

        if (this.duration == this.startingDuration) {

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractGuardianCard) {
                    if (((AbstractGuardianCard) c).socketCount > 0 || c.hasTag(GuardianMod.GEM)) {
                        if (((AbstractGuardianCard) c).sockets.size() > 0) {
                            hitCount += ((AbstractGuardianCard) c).sockets.size();
                            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));

                        }
                        if (c.hasTag(GuardianMod.GEM)) {
                            hitCount++;
                            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));

                        }
                    }
                }
            }

            if (hitCount > 0) {
                AbstractDungeon.topLevelEffectsQueue.add(new ScreenOnFireEffect());

                for (int i = 0; i < hitCount; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(this.info, AttackEffect.FIRE));
                }
            }


        }

        this.tickDuration();
    }
}
