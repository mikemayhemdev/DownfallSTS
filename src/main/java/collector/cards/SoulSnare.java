package collector.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class SoulSnare extends AbstractCollectorCard {
    public final static String ID = makeID("SoulSnare");
    // intellij stuff skill, enemy, basic, , , , , 1, 1

    public SoulSnare() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p,m);
        applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        //TODO - Actual soul snare effect/power
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}