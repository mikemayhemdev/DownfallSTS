package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WideGuard extends AbstractBronzeCard {

    public final static String ID = makeID("WideGuard");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 14;
    private static final int UPG_BLOCK = 4;

    public WideGuard() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        tags.add(AutomatonMod.SHIELD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}