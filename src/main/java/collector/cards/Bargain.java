package collector.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bargain extends AbstractCollectorCard {
    public final static String ID = makeID("Bargain");

    public Bargain() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(2,cardStrings.EXTENDED_DESCRIPTION[0],false,false,card -> card.cost > -1,list -> {
           list.get(1).modifyCostForCombat(list.get(0).cost);
            list.get(0).modifyCostForCombat(list.get(1).cost);
        }));
    }

    @Override
    public void upp() {
        selfRetain = true;
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}