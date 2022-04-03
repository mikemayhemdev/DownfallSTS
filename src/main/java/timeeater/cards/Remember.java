package timeeater.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Safety;
import com.megacrit.cardcrawl.cards.tempCards.Smite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.makeInHand;

public class Remember extends AbstractTimeEaterCard {
    public final static String ID = makeID("Remember");
    // intellij stuff skill, self, rare, , , 10, 3, , 

    public Remember() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 10;
        cardToPreview.add(new Smite());
        cardToPreview.add(new Safety());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractCard q = new Smite();
        if (upgraded) q.upgrade();
        makeInHand(q);
        AbstractCard q2 = new Safety();
        if (upgraded) q2.upgrade();
        makeInHand(q2);
    }

    public void upp() {
        upgradeCardToPreview();
        uDesc();
    }
}