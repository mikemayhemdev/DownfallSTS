package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.BleedDebuff;
import awakenedOne.powers.HemorrhageDebuff;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.CardIgnore;

@Deprecated
@CardIgnore
public class RememberForm extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(RememberForm.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    boolean chant = false;

    public RememberForm() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (AbstractPower q : p.powers) {
            if (q.ID == HemorrhageDebuff.POWER_ID || q.ID == BleedDebuff.POWER_ID) {
                this.addToTop(new RemoveSpecificPowerAction(p, p, q));
            }
        }
    }


    @Override
    public void upp() {
        this.exhaust = false;
    }
}