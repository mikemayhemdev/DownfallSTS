package timeeater.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Feeble extends AbstractTimeEaterCard {
    public final static String ID = makeID("Feeble");
    // intellij stuff skill, none, curse, , , , , , 

    public Feeble() {
        super(ID, -2, CardType.CURSE, CardRarity.CURSE, CardTarget.NONE, CardColor.CURSE);
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerWhenDrawn() {
        superFlash();
        att(new DrawCardAction(1));
        applyToSelfTop(new VulnerablePower(adp(), 1, false));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public boolean canUpgrade() {
        return false;
    }

    public void upp() {
    }
}