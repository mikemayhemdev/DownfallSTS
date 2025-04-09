package collector.cards.collectibles;

import collector.powers.collectioncards.BookOfStabbingCardPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class BookOfStabbingCard extends AbstractCollectibleCard {
    public final static String ID = makeID(BookOfStabbingCard.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 2, 1

    public BookOfStabbingCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("STAB_BOOK_DEATH");
        applyToSelf(new BookOfStabbingCardPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}