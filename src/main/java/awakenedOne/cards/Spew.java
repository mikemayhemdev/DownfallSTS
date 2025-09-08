package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.patches.OnCreateCardSubscriber;
import awakenedOne.patches.OnLoseEnergyPowerPatch;
import awakenedOne.powers.OnLoseEnergyPower;
import awakenedOne.relics.OnLoseEnergyRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.atb;

public class Spew extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Spew.class.getSimpleName());
    private static final int COST = 1;

    public Spew() {
        super(ID, COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        loadJokeCardImage(this, makeBetaCardPath(Spew.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
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
        upgradeDamage(3);
    }
}