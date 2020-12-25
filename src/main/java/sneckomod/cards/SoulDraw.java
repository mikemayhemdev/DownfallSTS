package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class SoulDraw extends AbstractSneckoCard {

    public final static String ID = makeID("SoulDraw");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 1;

    public SoulDraw() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(SneckoMod.SNEKPROOF);
        tags.add(SneckoMod.RNG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int min = 0;
        if (upgraded) min = 1;
        int x = getRandomNum(1, magicNumber, this);
        if (x > 0)
            atb(new DrawCardAction(x));

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