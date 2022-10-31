package hermit.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import hermit.patches.EnumPatch;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.UUID;

public class GoldenBulletAction extends AbstractGameAction {
    //private static final Logger logger = LogManager.getLogger(AbstractCard.class.getName());
    private DamageInfo info;
    private UUID uuid;
    int amount;

    public GoldenBulletAction(AbstractCreature target, DamageInfo info, int amount, UUID targetUUID) {
        this.info = info;
        this.setValues(target, info);
        this.amount = amount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
        this.uuid = targetUUID;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX+MathUtils.random(-8f,8f), this.target.hb.cY+MathUtils.random(-8f,8f), EnumPatch.HERMIT_GUN));
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

                AbstractCard c;
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    if (c.uuid.equals(this.uuid)) {
                        System.out.println("Old Misc: " + c.misc);
                        System.out.println("Old Cost: " + c.cost);
                        if (c.misc < 3)
                        c.misc += this.amount;
                        c.updateCost(-this.amount);
                        System.out.println("New Misc: " + c.misc);
                        System.out.println("New Cost: " + c.cost);
                    }
                }

                for(var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext();) {
                    c = (AbstractCard)var1.next();
                    if (c.misc < 3)
                        c.misc += this.amount;
                    c.updateCost(-this.amount);
                    c.flash(Color.WHITE.cpy());
                }

            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
