package collector.cards;

import collector.powers.ReservePower;
import collector.powers.SoulbindPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.applyToSelf;

public class SoulSnare extends AbstractCollectorCard {
    public final static String ID = makeID(SoulSnare.class.getSimpleName());
    // intellij stuff skill, enemy, starter, , , , , 1, 1

    public SoulSnare() {
        super(ID, 3, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new SoulbindPower(m, 10));
        applyToSelf(new ReservePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}