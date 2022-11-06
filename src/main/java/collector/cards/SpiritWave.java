package collector.cards;

import collector.actions.HealTorchheadAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class SpiritWave extends AbstractCollectorCard {
    public final static String ID = makeID(SpiritWave.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public SpiritWave() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        pyre = true;
        baseMagicNumber = magicNumber = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        pyreCost();
        addToBot(new HealTorchheadAction(magicNumber));
        addToBot(new DrawCardAction(2));
    }

    public void upp() {
        upgradeMagicNumber(5);
    }
}