package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeID;

public class Unleash extends AbstractAwakenedCard {
    public final static String ID = makeID(Unleash.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public Unleash() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.selfRetain = true;
        this.exhaust = true;
        loadJokeCardImage(this, ID+".png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("Strength")) {
            int strAmt =p.getPower("Strength").amount;
            this.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p, strAmt), strAmt));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, strAmt), strAmt));
        }
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}