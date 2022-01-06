package collector.cards.Collectibles;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.IntangiblePower;

public class SilentTrophy extends AbstractCollectibleCard {
    public final static String ID = makeID("SilentTrophy");
    public static AbstractCard s;
    public SilentTrophy() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CollectorCardSource.BOTH);
        this.exhaust = true;
        s = (new Shiv()).makeCopy();
        s.upgrade();
        if (upgraded){
            cardsToPreview = s;
        } else cardsToPreview = new Shiv();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToFront(new IntangiblePower(p,1));
        if (!upgraded) {
            atb(new MakeTempCardInHandAction(new Shiv(), 2));
        } else
            atb(new MakeTempCardInHandAction( s, 2));
            applyToSelf(new BlurPower(p,1));
    }

    @Override
    public void upp() {
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}
