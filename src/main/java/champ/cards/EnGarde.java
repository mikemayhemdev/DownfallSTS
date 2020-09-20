package champ.cards;

import champ.powers.CounterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnGarde extends AbstractChampCard {

    public final static String ID = makeID("EnGarde");

    //stupid intellij stuff skill, self, common

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 3;

    public EnGarde() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        applyToSelf(new CounterPower(magicNumber));
        if (dcombo()) atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (p.hasPower(CounterPower.POWER_ID)) {
                    int x = p.getPower(CounterPower.POWER_ID).amount;
                    att(new ApplyPowerAction(p, p, new CounterPower(x), x));
                }
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}