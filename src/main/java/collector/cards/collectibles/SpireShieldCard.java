package collector.cards.collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;

public class SpireShieldCard extends AbstractCollectibleCard {
    public final static String ID = makeID(SpireShieldCard.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , 3, 1

    public SpireShieldCard() {
        super(ID, 2, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(p, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}