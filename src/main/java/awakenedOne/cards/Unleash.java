package awakenedOne.cards;

import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.*;
import static collector.util.Wiz.applyToSelf;

public class Unleash extends AbstractAwakenedCard {
    public final static String ID = makeID(Unleash.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public Unleash() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        //this.selfRetain = true;
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath( "Unleash.png"));
        this.cardsToPreview = new Miracle();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
 //       if (p.hasPower("Strength")) {
        //            int strAmt =p.getPower("Strength").amount;
        //            this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, strAmt), strAmt));
        //            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, strAmt), strAmt));
        //        }
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        applyToSelf(new AddCopyNextTurnPower(new Miracle()));
    }

    public void upp() {
        //upgradeBaseCost(1);
        upgradeMagicNumber(2);
    }
}