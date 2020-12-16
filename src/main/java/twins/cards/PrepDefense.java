package twins.cards;

import twins.DonuDecaMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrepDefense extends AbstractTwinsCard {

    public final static String ID = makeID("PrepDefense");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 5;

    public PrepDefense() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = BLOCK;
        cardsToPreview = new ReserveGuard();
        tags.add(DonuDecaMod.SHIELD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        makeInHand(new ReserveGuard());
    }

    public void upp() {
        upgradeBlock(1);
    }
}