package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Jackpot extends AbstractSneckoCard {

    public final static String ID = makeID("Jackpot");

    //stupid intellij stuff SKILL, SELF, UNCOMMON
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public Jackpot() {
        super(ID, 3, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        this.selfRetain = true;
        SneckoMod.loadJokeCardImage(this, "Jackpot.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
