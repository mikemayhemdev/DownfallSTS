package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class DesperatePrayer extends AbstractAwakenedCard {
    public final static String ID = makeID(DesperatePrayer.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public DesperatePrayer() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        loadJokeCardImage(this, makeBetaCardPath(DesperatePrayer.class.getSimpleName() + ".png"));
        exhaust = true;
        baseMagicNumber = magicNumber = 2;
        AbstractCard c = new Ceremony();
        c.upgrade();
        this.cardsToPreview = c;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = new Ceremony();
        c.upgrade();
        Wiz.atb(new MakeTempCardInHandAction(c, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}