package awakenedOne.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.topDeck;

public class MiracleWorker extends AbstractAwakenedCard {
    public final static String ID = makeID(MiracleWorker.class.getSimpleName());
    // intellij stuff skill, self, common, , , 9, 1, , 

    public MiracleWorker() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 9;
        cardsToPreview = new Miracle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractCard q = new Miracle();
        if (upgraded) q.upgrade();
        topDeck(q);
    }

    public void upp() {
        upgradeBlock(1);
        AbstractCard q2 = new Miracle();
        q2.upgrade();
        cardsToPreview = q2;
    }
}