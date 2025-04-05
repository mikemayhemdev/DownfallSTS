package collector.actions;

import collector.effects.ColoredVerticalAttackEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import gremlin.actions.PseudoDamageRandomEnemyAction;
import gremlin.relics.FragmentationGrenade;

import static collector.util.Wiz.att;

public class ScorchingRayAction extends AbstractGameAction {
    private final AbstractCard card;
    private static int stuff;

    public ScorchingRayAction(AbstractCard card, int cool) {
        this.card = card;
        this.actionType = ActionType.DAMAGE;
        this.startDuration = 0.1F;
        this.duration = this.startDuration;
        stuff = cool;
    }

    public void update() {
        isDone = true;
        AbstractMonster q = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (q != null) {
            card.applyPowers();
            card.calculateCardDamage(q);
            int storage = stuff;
            if (q.hasPower(VulnerablePower.POWER_ID)) {
                stuff = (stuff + (stuff/2));
            }
            att(new PseudoDamageRandomEnemyAction(q, new DamageInfo(AbstractDungeon.player, card.damage + stuff, DamageInfo.DamageType.NORMAL), AttackEffect.FIRE));
            att(new VFXAction(new ColoredVerticalAttackEffect(q.hb.x + MathUtils.random(q.hb.width / 3, ((q.hb.width / 3) * 2)), q.hb.cY, true, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1))));
            stuff = storage;
        }
    }
}
