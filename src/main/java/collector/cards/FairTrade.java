package collector.cards;

import collector.actions.DrawCardFromCollectionAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class FairTrade extends AbstractCollectorCard {
    public final static String ID = makeID(FairTrade.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , 3, 1

    public FairTrade() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            addToBot(new DrawCardFromCollectionAction());
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}