package collector.cards.CollectorCards.Skills;

import collector.CollectorCollection;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Contemplate extends AbstractCollectorCard {
    public final static String ID = makeID("Contemplate");
    public Contemplate() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> possCardsList = new ArrayList<>();
        CollectorCollection.combatCollection.shuffle();
        if (CollectorCollection.combatCollection.group.size() >= 3) {
            for (int i = 0; i < 3; i++) {
                possCardsList.add(CollectorCollection.combatCollection.group.get(i));
            }
        } else {
            possCardsList.addAll(CollectorCollection.combatCollection.group);
        }
        atb(new SelectCardsAction(possCardsList, 1, "Choose.", (cards) -> {
            if (upgraded){
                cards.get(0).upgrade();
            }
            CollectorCollection.combatCollection.removeCard(cards.get(0));
            AbstractDungeon.player.hand.addToTop(cards.get(0));
        }));
    }

    @Override
    public void upp() {
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}