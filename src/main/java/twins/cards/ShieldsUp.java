package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShieldsUp extends AbstractTwinsCard {

    public final static String ID = makeID("ShieldsUp");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 2;

    public ShieldsUp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        tags.add(DonuDecaMod.SHIELD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}