package collector.cards;

import collector.CollectorCollection;
import collector.actions.DrawCardFromCollectionAction;
import collector.cards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class Contemplate extends AbstractCollectorCard {
    public final static String ID = makeID(Contemplate.class.getSimpleName());
    // intellij stuff skill, self, basic, , , , , , 

    public Contemplate() {
        super(ID, 0, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            atb(new DrawCardFromCollectionAction());
        }
        else {
            if (!CollectorCollection.combatCollection.isEmpty()) {
                ArrayList<AbstractCard> options = new ArrayList<>();
                options.addAll(CollectorCollection.combatCollection.group);
                ArrayList<AbstractCard> toShow = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    if (options.isEmpty()) break;
                    toShow.add(options.remove(AbstractDungeon.cardRandomRng.random(options.size() - 1)));
                }
                atb(new SelectCardsCenteredAction(toShow, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
                    AbstractCard tar = cards.get(0);
                    CollectorCollection.combatCollection.removeCard(tar);
                    AbstractDungeon.player.drawPile.addToTop(tar);
                    att(new DrawCardAction(1));
                }));
            }
        }
    }

    public void upp() {
        uDesc();
    }
}