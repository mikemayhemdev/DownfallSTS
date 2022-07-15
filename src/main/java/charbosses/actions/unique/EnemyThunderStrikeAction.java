package charbosses.actions.unique;


import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.EnemyEmptyOrbSlot;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class EnemyThunderStrikeAction extends AbstractGameAction {
    private DamageInfo info = null;
    private AbstractCreature target;
    AbstractBossCard card;

    public EnemyThunderStrikeAction(AbstractBossCard card, AbstractCreature m) {
        this.target = m;
        this.card = card;
    }

    public void update() {
        if (!Settings.FAST_MODE) {
            this.addToTop(new WaitAction(0.1F));
        }

        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, new DamageInfo(card.owner, card.damage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            this.isDone = true;
            this.addToTop(new VFXAction(new LightningEffect(this.target.drawX, this.target.drawY)));
            this.addToTop(new VFXAction(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect)));
            this.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
        }

    }

}
