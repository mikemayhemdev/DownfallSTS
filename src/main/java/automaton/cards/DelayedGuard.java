package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;

import static automaton.AutomatonMod.makeBetaCardPath;

public class DelayedGuard extends AbstractBronzeCard {

    public final static String ID = makeID("DelayedGuard");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 7;
    private static final int UPG_BLOCK = 3;

    public DelayedGuard() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        thisEncodes();
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("DelayedGuard.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new NextTurnBlockPower(p, block));
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}