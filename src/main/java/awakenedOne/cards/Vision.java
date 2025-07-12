package awakenedOne.cards;

import awakenedOne.ui.OrbitingSpells;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.updateTimeOffsets;
import static awakenedOne.util.Wiz.atb;

public class Vision extends AbstractAwakenedCard {
    public final static String ID = makeID(Vision.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Vision() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, makeBetaCardPath("Vision.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsInHandAction(1, "", (cards) -> {
            for (AbstractCard q : cards) {
                AbstractCard q2 = q.makeStatEquivalentCopy();
                //q2.updateCost(-99);
                for (int i = 0; i < magicNumber; i++) {
                    AbstractCard card = q2.makeStatEquivalentCopy();
                    spellCards.add(new OrbitingSpells.CardRenderInfo(card));
                    updateTimeOffsets();
                }
            }
        }));
    }

    public void upp() {
        //upgradeBaseCost(0);
        upgradeMagicNumber(1);
    }
}