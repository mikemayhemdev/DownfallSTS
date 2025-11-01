package collector.cards;

import automaton.actions.EasyXCostAction;
import collector.effects.ColoredVerticalAttackEffect;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import gremlin.actions.PseudoDamageRandomEnemyAction;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class ScorchingRay extends AbstractCollectorCard {
    public final static String ID = makeID(ScorchingRay.class.getSimpleName());
    // intellij stuff attack, enemy, common, 4, 1, , , 4,

    public ScorchingRay() {
        super(ID, -1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 8;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                //ScorchingRayAction couldn't cut it.
                AbstractMonster q = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                atb(new VFXAction(new ColoredVerticalAttackEffect(q.hb.x + MathUtils.random(q.hb.width / 3, ((q.hb.width / 3) * 2)), q.hb.cY, true, new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1))));
                dmg(q, AbstractGameAction.AttackEffect.FIRE);
            }
            return true;
        }));
    }

    public void upp() {
        upgradeDamage(3);
    }
}