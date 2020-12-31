package collector.cards;

import collector.CollectorCollection;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Contemplate extends AbstractCollectorCard {
    public final static String ID = makeID("Contemplate");
    public Contemplate() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> possCardsList = new ArrayList<>();
        CollectorCollection.combatCollection.shuffle();
        for (int i = 0; i < 3; i++) {
            possCardsList.add(CollectorCollection.combatCollection.group.get(i));
        }
        atb(new SelectCardsAction(possCardsList, 1, "Choose.", (cards) -> {
            CollectorCollection.combatCollection.moveToHand(cards.get(0));
        }));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}