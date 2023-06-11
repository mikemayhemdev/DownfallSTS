package collector.cards;

import collector.powers.DoomPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class ShadowWrappings extends AbstractCollectorCard {
    public final static String ID = makeID(ShadowWrappings.class.getSimpleName());
    // intellij stuff attack, enemy, common, 3, 1, , , 2, 

    public ShadowWrappings() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new DoomPower(m, magicNumber));
    }

    public void upp() {
        upgradeDamage(4);
    }
}