package awakenedOne.cards;

import awakenedOne.powers.EnemyHexedPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class SiphonEnergy extends AbstractAwakenedCard {
    public final static String ID = makeID(SiphonEnergy.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public SiphonEnergy() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        magicNumber = baseMagicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(SiphonEnergy.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (m.hasPower(EnemyHexedPower.POWER_ID)) {
            this.addToBot(new DrawCardAction(magicNumber));

        }
    }

    @Override
    public void upp() {
        //upgradeDamage(3);
        upgradeMagicNumber(1);
    }
}