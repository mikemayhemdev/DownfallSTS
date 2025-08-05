package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class DarkPower extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(DarkPower.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public DarkPower() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 11;
        this.cardsToPreview = new Ceremony();
        this.tags.add(AwakenedOneMod.CHANT);
        loadJokeCardImage(this, makeBetaCardPath(DarkPower.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractCard c = new Ceremony();
        if (upgraded) {
            c.upgrade();
        }
        Wiz.atb(new MakeTempCardInHandAction(c, 1));
    }


    @Override
    public void upp() {
        cardsToPreview.upgrade();
        upgradeBlock(3);
    }
}