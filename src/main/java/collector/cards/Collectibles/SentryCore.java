package collector.cards.Collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.actions.PlaceCardsInHandIntoStasisAction;

public class SentryCore extends AbstractCollectibleCard {
    public final static String ID = makeID("SentryCore");

    public SentryCore() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        magicNumber = baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new PlaceCardsInHandIntoStasisAction(p,magicNumber,false));
    }

    @Override
    public void upp() {
        upgradeBlock(2);
    }
}
