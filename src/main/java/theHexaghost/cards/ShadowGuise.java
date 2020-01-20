package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShadowGuise extends AbstractHexaCard {

    public final static String ID = makeID("ShadowGuise");

    //stupid intellij stuff SKILL, SELF, SPECIAL

    private static final int BLOCK = 14;
    private static final int UPG_BLOCK = 4;

    public ShadowGuise() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        baseBlock = BLOCK;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}