package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrepDefense extends AbstractBronzeCard {

    public final static String ID = makeID("PrepDefense");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 8;

    public PrepDefense() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        cardsToPreview = new ReserveGuard();
        tags.add(AutomatonMod.SHIELD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        makeInHand(new ReserveGuard());
    }

    public void upp() {
        upgradeBlock(2);
    }
}