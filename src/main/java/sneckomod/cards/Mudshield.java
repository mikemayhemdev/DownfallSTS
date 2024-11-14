package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.MudshieldPower;

public class Mudshield extends AbstractSneckoCard {

    public final static String ID = makeID("Mudshield");

    //stupid intellij stuff POWER, SELF, RARE

    public Mudshield() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;

        SneckoMod.loadJokeCardImage(this, "Mudshield.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MudshieldPower(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}