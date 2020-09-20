package champ.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

public class HoldFirm extends AbstractChampCard {

    public final static String ID = makeID("HoldFirm");

    //stupid intellij stuff skill, self, rare

    private static final int BLOCK = 16;
    private static final int UPG_BLOCK = 6;

    public HoldFirm() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (dcombo()) {
            applyToSelf(new BlurPower(p, 1));
        }
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}