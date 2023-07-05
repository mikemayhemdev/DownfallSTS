package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class ChosenCard extends AbstractCollectibleCard {
    public final static String ID = makeID(ChosenCard.class.getSimpleName());
    // intellij stuff skill, enemy, common, , , , , 3, 2

    public ChosenCard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m.getIntentBaseDmg() <= -1) {
            applyToEnemy(m, new WeakPower(m, magicNumber, false));
            applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        }
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}