package collector.cards.Collectibles;

import collector.cards.CollectorCards.AbstractCollectorCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CultistFeather extends AbstractCollectibleCard {
    public final static String ID = makeID("CultistFeather");

    public CultistFeather() {
        super(ID, 0, CardType.POWER, CardRarity.COMMON, CardTarget.NONE, AbstractCollectorCard.CollectorCardSource.BOTH);
        baseDamage = 13;
        baseBlock = 1;
        magicNumber = baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToFront(new StrengthPower(p,magicNumber));
        if (upgraded){
            applyToFront(new DexterityPower(p,magicNumber));
        }
    }

    @Override
    public void upp() {
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}