package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RepairPower;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;
@Deprecated
@CardIgnore
public class StitchUp extends AbstractAwakenedCard {
    public final static String ID = makeID(StitchUp.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public StitchUp() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new RepairPower(p, this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}