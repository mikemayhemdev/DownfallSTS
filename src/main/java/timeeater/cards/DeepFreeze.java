package timeeater.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.RetrieveLessNextTurnPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class DeepFreeze extends AbstractTimeEaterCard {
    public final static String ID = makeID("DeepFreeze");
    // intellij stuff skill, self, common, , , 10, 3, , 

    public DeepFreeze() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new RetrieveLessNextTurnPower(1));
    }

    public void upp() {
        upgradeBlock(3);
    }
}