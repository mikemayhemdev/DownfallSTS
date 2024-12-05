package collector.cards;

import collector.powers.DoomPower;
import collector.powers.HealIfDieThisTurnPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class Finalize extends AbstractCollectorCard {
    public final static String ID = makeID(Finalize.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 25, 5

    public Finalize() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 20;
        baseSecondMagic = secondMagic = 5;
        tags.add(CardTags.HEALING);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new HealIfDieThisTurnPower(m, secondMagic));
        applyToEnemy(m, new DoomPower(m, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(4);
        upgradeSecondMagic(2);
    }
}