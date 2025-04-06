package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.OverwhelmingPresencePower;

public class OverwhelmingPresence extends AbstractSneckoCard {

    public final static String ID = makeID("OverwhelmingPresence");

    public OverwhelmingPresence() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        SneckoMod.loadJokeCardImage(this, "OverwhelmingPresence.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new OverwhelmingPresencePower(p, this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}
