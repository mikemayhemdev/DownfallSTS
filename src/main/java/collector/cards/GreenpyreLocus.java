package collector.cards;

import collector.CollectorCollection;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;

public class GreenpyreLocus extends AbstractCollectorCard {
    public final static String ID = makeID(GreenpyreLocus.class.getSimpleName());
    // intellij stuff skill, self, uncommon, , , , , 1, 1

    public GreenpyreLocus() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        exhaust = true;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsAction(CollectorCollection.combatCollection.group, magicNumber, cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            for (AbstractCard toMove : cards) {
                att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        CollectorCollection.combatCollection.moveToHand(toMove);
                    }
                });
            }
        }));
    }

    public void upp() {
        upgradeMagicNumber(1);
        uDesc();
    }
}