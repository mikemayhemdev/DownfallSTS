package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;
import hermit.util.Wiz;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class InflictAgony extends AbstractCollectorCard {
    public final static String ID = makeID(InflictAgony.class.getSimpleName());
    // intellij stuff attack, enemy, common, 13, 5, , , , 

    public InflictAgony() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 14;
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new ViceCrushEffect(m.hb.cX, m.hb.cY)));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        if (!isAfflicted(m)) {
            applyToEnemy(m, new WeakPower(m, magicNumber, false));
            applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        }
    }

    public void upp() {
        upgradeDamage(4);
        upgradeMagicNumber(1);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = Wiz.getEnemies().stream().noneMatch(q -> (!q.hasPower(WeakPower.POWER_ID) || !q.hasPower(VulnerablePower.POWER_ID))) ? BLUE_BORDER_GLOW_COLOR : AbstractCard.GOLD_BORDER_GLOW_COLOR;
    }
}