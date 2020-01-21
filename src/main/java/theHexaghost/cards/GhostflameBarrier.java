package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.powers.GhostFlameBarrierPower;

public class GhostflameBarrier extends AbstractHexaCard {

    public final static String ID = makeID("GhostflameBarrier");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 4;

    private static final int MAGIC = 4;
    private static final int UPG_MAGIC = 2;

    public GhostflameBarrier() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new GhostFlameBarrierPower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}