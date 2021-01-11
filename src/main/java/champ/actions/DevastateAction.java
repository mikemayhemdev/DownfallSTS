package champ.actions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.stances.NeutralStance;

import java.util.UUID;

public class DevastateAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private int increaseAmount;
    private DamageInfo info;
    private UUID uuid;

    public DevastateAction(AbstractCreature target, DamageInfo info, int incAmount, UUID targetUUID) {
        this.info = info;
        setValues(target, info);
        this.increaseAmount = incAmount;
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1F;
        this.uuid = targetUUID;
    }

    public void update() {
        if ((this.duration == 0.1F) && (this.target != null)) {
            AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));

            this.target.damage(this.info);

                for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                    if (c.uuid.equals(this.uuid)) {
                        c.misc += this.increaseAmount;
                        c.applyPowers();
                        c.baseDamage = c.misc;
                        c.isDamageModified = false;
                    }
                for (AbstractCard c : com.megacrit.cardcrawl.helpers.GetAllInBattleInstances.get(this.uuid)) {
                    c.misc += this.increaseAmount;
                    c.applyPowers();
                    c.baseDamage = c.misc;
                }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        tickDuration();
    }
}

