package awakenedOne.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.shuffleIn;

public class DesperatePrayer extends AbstractAwakenedCard {
    public final static String ID = makeID(DesperatePrayer.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public DesperatePrayer() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
        c.modifyCostForCombat(-999);
        if (upgraded) {
            c.upgrade();
        }
        shuffleIn(c);
    }

    public void upp() {
    }
}