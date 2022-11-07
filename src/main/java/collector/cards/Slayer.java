package collector.cards;

import collector.powers.SoulSnarePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.order;

public class Slayer extends AbstractCollectorCard {
    public final static String ID = makeID(Slayer.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Slayer() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 18;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!m.hasPower(SoulSnarePower.POWER_ID)){
            return false;
        }
        return super.canUse(p, m);
    }

    public void upp() {
        upgradeDamage(4);
    }
}