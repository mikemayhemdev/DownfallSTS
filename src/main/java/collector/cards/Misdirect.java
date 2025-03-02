package collector.cards;

import collector.powers.NextTurnReservePower;
import com.megacrit.cardcrawl.actions.animations.AnimateJumpAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToSelf;
import static collector.util.Wiz.atb;

public class Misdirect extends AbstractCollectorCard {
    public final static String ID = makeID(Misdirect.class.getSimpleName());
    // intellij stuff skill, self, common, , , , , , 

    public Misdirect() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AnimateJumpAction(p));
        blck();
        applyToSelf(new NextTurnReservePower(1));
    }

    public void upp() {
        upgradeBlock(2);
    }
}