package sneckomod.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.ToxicPersonalityPower;

public class ToxicPersonality extends AbstractSneckoCard {

    public final static String ID = makeID("ToxicPersonality");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public ToxicPersonality() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "ToxicPersonality.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new ToxicPersonalityPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}