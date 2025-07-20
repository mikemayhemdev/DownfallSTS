package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Unleash extends AbstractAwakenedCard {
    public final static String ID = makeID(Unleash.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public Unleash() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.selfRetain = true;
        this.exhaust = true;
        baseMagicNumber = magicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath( "Unleash.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
 //       if (p.hasPower("Strength")) {
        //            int strAmt =p.getPower("Strength").amount;
        //            this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, strAmt), strAmt));
        //            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, strAmt), strAmt));
        //        }
        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
        atb(new GainEnergyAction(1));
    }

    public void upp() {
        //upgradeBaseCost(1);
        upgradeMagicNumber(2);
    }
}