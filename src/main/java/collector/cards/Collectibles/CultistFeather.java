package collector.cards.Collectibles;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CultistFeather extends AbstractCollectibleCard {
    public final static String ID = makeID("NobsBoneClub");

    public CultistFeather() {
        super(ID, 0, CardType.POWER, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 13;
        baseBlock = 1;
        baseMagicNumber = 1;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p,magicNumber));
        if (upgraded){
            applyToSelf(new DexterityPower(p,magicNumber));
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(4);
        this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}