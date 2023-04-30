package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applySuffering;

public class SoulStitch extends AbstractCollectorCard {
    public final static String ID = makeID(SoulStitch.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public SoulStitch() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        applySuffering(m, magicNumber);
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}