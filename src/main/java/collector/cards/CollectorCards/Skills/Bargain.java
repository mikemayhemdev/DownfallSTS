package collector.cards.CollectorCards.Skills;

import collector.cards.CollectorCards.AbstractCollectorCard;
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
                int cost1 =list.get(0).cost;
                int cost2 =list.get(1).cost;
                list.get(1).cost = cost1;
                list.get(0).cost = cost2;
        }));
        }
@Override
public void upp() {
        selfRetain = true;
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
        }
}