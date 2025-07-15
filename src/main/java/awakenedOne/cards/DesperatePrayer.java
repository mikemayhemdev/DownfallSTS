package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class DesperatePrayer extends AbstractAwakenedCard {
    public final static String ID = makeID(DesperatePrayer.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public DesperatePrayer() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(DesperatePrayer.class.getSimpleName() + ".png"));
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard q = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
        if (q.rarity == CardRarity.RARE) {
            while (q.rarity == CardRarity.RARE) {
                q = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
            }
        }
        q.modifyCostForCombat(-999);
        addToBot(new MakeTempCardInDrawPileAction(q, 1, false, true));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}