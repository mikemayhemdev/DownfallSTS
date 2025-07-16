package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.BigGunsBlockPower;
import sneckomod.powers.BigGunsStrengthPower;

public class BlunderGuard extends AbstractSneckoCard {

    public final static String ID = makeID("BlunderGuard");

    //stupid intellij stuff POWER, SELF, RARE
    private static final int BASE_SILLY = 6;
    private static final int UPG_SILLY = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public BlunderGuard() {
        super(ID, 0, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseSilly = silly = 6;
        SneckoMod.loadJokeCardImage(this, "BlunderGuard.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new BigGunsBlockPower(this.silly));
        applyToSelf(new BigGunsStrengthPower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeSilly(UPG_SILLY);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}