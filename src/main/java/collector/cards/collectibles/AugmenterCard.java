package collector.cards.collectibles;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class AugmenterCard extends AbstractCollectibleCard {
    public final static String ID = makeID(AugmenterCard.class.getSimpleName());
    // intellij stuff power, self, uncommkon, , , , , 3, 2

    public AugmenterCard() {
        super(ID, 0, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new LoseHPAction(p, p, 3));
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}