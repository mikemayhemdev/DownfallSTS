package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.powers.SnakeEyesPower;

public class SnakeEyes extends AbstractSneckoCard {

    public final static String ID = makeID("SnakeEyes");

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    //burst clone

    public SnakeEyes() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
      //  exhaust = true;
        SneckoMod.loadJokeCardImage(this, "SnakeEyes.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SnakeEyesPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
