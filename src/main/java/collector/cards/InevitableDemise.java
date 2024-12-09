
package collector.cards;

import collector.powers.DemisePower;
import collector.powers.DoomPower;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.InversionBeamEffect;
import hermit.util.Wiz;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class InevitableDemise extends AbstractCollectorCard {
    public final static String ID = makeID(InevitableDemise.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 4, , , ,

    public InevitableDemise() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10;
       // baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new InversionBeamEffect(MathUtils.random(m.hb.x + (m.hb.width / 3F), m.hb.x + ((m.hb.width / 3F) * 2F)))));
        dmg(m, AbstractGameAction.AttackEffect.NONE);
        applyToEnemy(m, new DemisePower(m, 1));
    }

    public void upp() {
        upgradeDamage(3);
       // upgradeMagicNumber(3);
    }
}

