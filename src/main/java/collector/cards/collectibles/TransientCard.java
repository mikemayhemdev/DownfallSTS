package collector.cards.collectibles;

import collector.powers.collectioncards.TransientCardPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class TransientCard extends AbstractCollectibleCard {
    public final static String ID = makeID(TransientCard.class.getSimpleName());
    // intellij stuff power, self, uncommon, , , , , 6, 3

    public TransientCard() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new TransientCardPower());
    }

    public void upp() {
        upgradeMagicNumber(3);
    }
}