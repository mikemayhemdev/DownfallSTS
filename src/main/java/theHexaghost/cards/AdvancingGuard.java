package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.AdvanceAction;

public class AdvancingGuard extends AbstractHexaCard {

    public final static String ID = makeID("AdvancingGuard");

    //stupid intellij stuff SKILL, SELF, COMMON

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    public AdvancingGuard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new AdvanceAction(false));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}