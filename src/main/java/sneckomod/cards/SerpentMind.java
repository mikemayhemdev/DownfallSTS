package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.powers.SerpentMindPower;

public class SerpentMind extends AbstractSneckoCard {

    public final static String ID = makeID("SerpentMind");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int MAGIC = 1;

    public SerpentMind() {
        super(ID,  3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SerpentMindPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}