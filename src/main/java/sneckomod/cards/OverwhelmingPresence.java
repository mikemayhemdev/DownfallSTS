package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.OverwhelmingPresencePower;

public class OverwhelmingPresence extends AbstractSneckoCard {

    public final static String ID = makeID("OverwhelmingPresence");

    public OverwhelmingPresence() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "OverwhelmingPresence.png");
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new OverwhelmingPresencePower(p, 1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
