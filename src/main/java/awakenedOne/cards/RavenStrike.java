package awakenedOne.cards;

import awakenedOne.powers.ManaburnPower;
import awakenedOne.powers.OnLoseEnergyPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.isInCombat;

public class RavenStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(RavenStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public RavenStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        //this.addToBot(new BetterDiscardPileToHandAction(1));

        if (!m.isDead && !m.isDying) {
            for (AbstractPower power : m.powers) {
                if (power instanceof OnLoseEnergyPower) {
                    ((OnLoseEnergyPower) power).LoseEnergyAction(1);
                }
            }
        }

//        if (upgraded) {
//            if (!m.isDead && !m.isDying) {
//                for (AbstractPower power : m.powers) {
//                    if (power instanceof OnLoseEnergyPower) {
//                        ((OnLoseEnergyPower) power).LoseEnergyAction(1);
//                    }
//                }
//            }
//        }

    }

    @Override
    public void triggerOnGlowCheck() {
        boolean hasStr = false;
        if (isInCombat()) {
            for (AbstractMonster m : monsterList()) {
                if (m.hasPower(ManaburnPower.POWER_ID)) {
                    if (m.getPower(ManaburnPower.POWER_ID).amount > 0) {
                        hasStr = true;
                        break;
                    }
                }
            }

            glowColor = (hasStr) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
        }
    }


    @Override
    public void upp() {
        upgradeDamage(3);
    }
}