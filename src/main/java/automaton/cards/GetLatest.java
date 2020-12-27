package automaton.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GetLatest extends AbstractBronzeCard {

    public final static String ID = makeID("GetLatest");

    //stupid intellij stuff skill, self, uncommon

    public GetLatest() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard qCardGet = SpaghettiCode.getRandomEncode();
        qCardGet.modifyCostForCombat(-99);
        atb(new MakeTempCardInHandAction(qCardGet, true));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}