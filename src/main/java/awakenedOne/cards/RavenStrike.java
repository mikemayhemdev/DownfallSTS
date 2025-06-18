package awakenedOne.cards;

import awakenedOne.patches.OnLoseEnergyPowerPatch;
import awakenedOne.powers.ManaburnPower;
import awakenedOne.powers.OnLoseEnergyPower;
import awakenedOne.relics.OnLoseEnergyRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.isInCombat;

public class RavenStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(RavenStrike.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public RavenStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        tags.add(CardTags.STRIKE);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

        if (this.costForTurn > 0 && !this.freeToPlay() && !this.freeToPlayOnce) {
            OnLoseEnergyPowerPatch.EnergyLostThisCombat = OnLoseEnergyPowerPatch.EnergyLostThisCombat + this.costForTurn;

            for (AbstractPower playerPower : AbstractDungeon.player.powers) {
                if (playerPower instanceof OnLoseEnergyPower) {
                    ((OnLoseEnergyPower) playerPower).LoseEnergyAction(this.costForTurn);
                }
            }

            for (AbstractRelic relic : AbstractDungeon.player.relics) {
                if (relic instanceof OnLoseEnergyRelic) {
                    ((OnLoseEnergyRelic) relic).LoseEnergyAction(this.costForTurn);
                }
            }

            for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
                if (!m2.isDead && !m2.isDying) {
                    for (AbstractPower power : m2.powers) {
                        if (power instanceof OnLoseEnergyPower) {
                            ((OnLoseEnergyPower) power).LoseEnergyAction(this.costForTurn);
                        }
                    }
                }
            }
        }
    }


    @Override
    public void upp() {
        this.exhaust = false;
    }
}