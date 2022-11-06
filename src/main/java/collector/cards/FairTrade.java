package collector.cards;

import collector.CollectorMod;
import collector.actions.DrawCardFromCollectionAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;
import static collector.util.Wiz.atb;

public class FairTrade extends AbstractCollectorCard {
    public final static String ID = makeID(FairTrade.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public FairTrade() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        pyre = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        pyreCost();
        for (int i = 0; i < magicNumber; i++) {
            atb(new DrawCardFromCollectionAction());
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}