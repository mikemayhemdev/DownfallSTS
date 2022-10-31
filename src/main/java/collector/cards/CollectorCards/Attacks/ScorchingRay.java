package collector.cards.CollectorCards.Attacks;

import collector.CollectorMod;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.InversionBeamEffect;

public class ScorchingRay extends AbstractCollectorCard {
    public final static String ID = makeID("ScorchingRay");

    public ScorchingRay() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new InversionBeamEffect(m.drawX)));
        for (AbstractPower po : m.powers){
            if (CollectorMod.AfflictionMatch(po.ID)){
                addToBot(new VFXAction(new InversionBeamEffect(m.drawX)));
                atb(new DamageAction(m,new DamageInfo(p,damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}