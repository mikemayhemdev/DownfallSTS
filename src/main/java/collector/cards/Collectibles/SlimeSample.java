package collector.cards.Collectibles;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SlimeSample extends AbstractCollectibleCard {
    public final static String ID = makeID("SlimeSample");

    public SlimeSample() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        magicNumber =  baseMagicNumber = 1;
        this.exhaust = true;
        cardsToPreview = new Slimed();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber));
        atb(new MakeTempCardInHandAction(new Slimed()));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}