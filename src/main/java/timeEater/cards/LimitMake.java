package timeEater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class LimitMake extends AbstractTimeCard {

    public final static String ID = makeID("LimitMake");

    // intellij stuff skill, self, rare

    public LimitMake() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (p.hasPower(StrengthPower.POWER_ID)) {
                    int x = p.getPower(StrengthPower.POWER_ID).amount;
                    applyToSelfTop(new LoseStrengthPower(p, x));
                    applyToSelfTop(new StrengthPower(p, x));
                }
            }
        });
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}