package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.cards.Sear;
import timeeater.actions.EasyXCostAction;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.HastePower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Collapse extends AbstractTimeEaterCard {
    public final static String ID = makeID("Collapse");
    // intellij stuff skill, self, rare, , , , , 2, 1

    public Collapse() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new SearingBlow();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            AbstractCard q = new SearingBlow();
            for (int i = 0; i < effect * params[0]; i++) {
                att(new UpgradeSpecificCardAction(q));
            }
            att(new SuspendAction(q));
            return true;
        }, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}