package collector.cards;

import collector.CollectorCollection;
import collector.actions.DrawCardFromCollectionAction;
import collector.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class Contemplate extends AbstractCollectorCard {
    public final static String ID = makeID(Contemplate.class.getSimpleName());
    // intellij stuff skill, self, basic, , , , , , 

    public Contemplate() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            atb(new DrawCardFromCollectionAction());
        } else {
            ArrayList<AbstractCard> possCards = new ArrayList<>();
            possCards.addAll(CollectorCollection.combatCollection.group);
            ArrayList<AbstractCard> choices = new ArrayList<>();
            int i = 0;
            while (i < 3 && !possCards.isEmpty()) {
                AbstractCard q = Wiz.getRandomItem(possCards, AbstractDungeon.cardRandomRng);
                if (!choices.stream().anyMatch(q2 -> q2.cardID.equals(q.cardID))) {
                    choices.add(q);
                }
                possCards.remove(q);
            }
            atb(new SelectCardsCenteredAction(choices, 1, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
                AbstractCard tar = cards.get(0);
                CollectorCollection.combatCollection.removeCard(tar);
                AbstractDungeon.player.drawPile.addToTop(tar);
                att(new DrawCardAction(1));
            }));
        }
    }

    public void upp() {
        uDesc();
    }
}