package awakenedOne.cards;

import awakenedOne.powers.OnLoseEnergyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static awakenedOne.AwakenedOneMod.makeID;

public class RavenStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(RavenStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public RavenStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        //this.addToBot(new BetterDiscardPileToHandAction(1));
        if (!m.isDead && !m.isDying) {
            for (AbstractPower power : m.powers) {
                if (power instanceof OnLoseEnergyPower) {
                    ((OnLoseEnergyPower) p).LoseEnergyAction(1);
                }
            }
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
    }
}