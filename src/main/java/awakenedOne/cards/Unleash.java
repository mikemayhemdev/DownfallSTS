package awakenedOne.cards;

import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import guardian.powers.NextTurnGainTemporaryStrengthPower;

import static awakenedOne.AwakenedOneMod.*;
import static collector.util.Wiz.applyToSelf;

public class Unleash extends AbstractAwakenedCard {
    public final static String ID = makeID(Unleash.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public Unleash() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath( "Unleash.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new NextTurnGainTemporaryStrengthPower(p, magicNumber)));
        this.addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, 1), 1));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}