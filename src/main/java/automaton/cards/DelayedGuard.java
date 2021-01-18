package automaton.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

public class DelayedGuard extends AbstractBronzeCard {

    public final static String ID = makeID("DelayedGuard");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;

    public DelayedGuard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new NextTurnBlockPower(p, block));
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}