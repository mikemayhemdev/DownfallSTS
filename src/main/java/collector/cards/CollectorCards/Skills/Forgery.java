package collector.cards.CollectorCards.Skills;

import collector.CollectorCollection;
import collector.cards.CollectorCards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Forgery extends AbstractCollectorCard {
    public final static String ID = makeID("Forgery");
    public Forgery() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
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
        atb(new SelectCardsAction(possCardsList, magicNumber, "Choose.", (cards) -> {
            if (upgraded && !cards.get(0).upgraded){
                cards.get(0).upgrade();
            }
            AbstractCard c = cards.get(0).makeStatEquivalentCopy();
            c.rarity = CardRarity.RARE;
            AbstractDungeon.player.hand.addToTop(c);
            if (upgraded){
                AbstractCard c1 = cards.get(1).makeStatEquivalentCopy();
                c1.rarity = CardRarity.RARE;
                AbstractDungeon.player.hand.addToTop(c1);
            }
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        initializeDescription();
    }
}
