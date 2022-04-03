package timeeater.cards;

import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import timeeater.actions.SuspendAction;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.applyToSelf;
import static timeeater.util.Wiz.atb;

public class ThrowLifeline extends AbstractTimeEaterCard {
    public final static String ID = makeID("ThrowLifeline");
    // intellij stuff skill, self, rare, , , , , 2, -1

    public ThrowLifeline() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new Apparition();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new VulnerablePower(p, magicNumber, false));
        for (int i = 0; i < 2; i++) {
            atb(new SuspendAction(new Apparition()));
        }
    }

    public void upp() {
        upgradeMagicNumber(-1);
    }
}