/*

package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import guardian.GuardianMod;
import guardian.orbs.StasisOrb;
import guardian.powers.zzzGuardianModePower.BronzeOrbBlockPower;
import guardian.powers.zzzGuardianModePower.BronzeOrbDamagePower;
import guardian.powers.zzzGuardianModePower.BronzeOrbExplodePower;
import guardian.powers.zzzGuardianModePower.BronzeOrbWeakenPower;

import kobting.friendlyminions.characters.AbstractPlayerWithMinions;


public class SpawnBronzeOrbAction extends AbstractGameAction {
    private AbstractPlayer playerIn;
    private powerTypes powerToAdd;
    private int powerAmount;
    private int powerAmount2;
    private powerTypes power2ToAdd;
    private int power2Amount;
    private int power2Amount2;

    public SpawnBronzeOrbAction(AbstractPlayer player, powerTypes powerToAdd, int powerAmount, int powerAmount2, powerTypes power2ToAdd, int power2Amount, int power2Amount2) {
    this(player,powerToAdd,powerAmount,powerAmount2);
    this.power2ToAdd = power2ToAdd;
    this.power2Amount = power2Amount;
    this.power2Amount2 = power2Amount2;
    }


    public SpawnBronzeOrbAction(AbstractPlayer player, powerTypes powerToAdd, int powerAmount, int powerAmount2) {
        this.playerIn = player;
        this.actionType = ActionType.DAMAGE;
        this.powerToAdd = powerToAdd;
        this.powerAmount = powerAmount;
        this.powerAmount2 = powerAmount2;
    }

    public void update() {
        if (playerIn instanceof AbstractPlayerWithMinions) {
            AbstractPlayerWithMinions player = (AbstractPlayerWithMinions) playerIn;
            int summonCount = player.minions.monsters.size();
            if (summonCount == 0) {
                GuardianMod.bronzeOrbInPlay = new BronzeOrbWhoReallyLikesDefectForSomeReason(-850, 300);

                if (powerToAdd != null){
                    addPower(powerToAdd, powerAmount, powerAmount2);
                }

                if (power2ToAdd != null){
                    addPower(power2ToAdd, power2Amount, power2Amount2);
                }

                player.addMinion(GuardianMod.bronzeOrbInPlay);
            }
    }

        this.isDone = true;
    }

    public void addPower(powerTypes pT, int amount1, int amount2){
        switch (pT){
            case DAMAGE:
                GuardianMod.bronzeOrbInPlay.powers.add(new BronzeOrbDamagePower(GuardianMod.bronzeOrbInPlay, amount1));
                break;
            case EXPLODE:
                GuardianMod.bronzeOrbInPlay.powers.add(new BronzeOrbExplodePower(GuardianMod.bronzeOrbInPlay, amount1, amount2));
                break;
            case THORNS:
                GuardianMod.bronzeOrbInPlay.powers.add(new ThornsPower(GuardianMod.bronzeOrbInPlay, amount1));
                break;
            case DEFEND:
                GuardianMod.bronzeOrbInPlay.powers.add(new BronzeOrbBlockPower(GuardianMod.bronzeOrbInPlay, amount1));
                break;
            case REPULSE:
                GuardianMod.bronzeOrbInPlay.powers.add(new BronzeOrbWeakenPower(GuardianMod.bronzeOrbInPlay, amount1));
                break;
            default:
                break;
        }
    }

    public enum powerTypes {
        EXPLODE,
        THORNS,
        DEFEND,
        REPULSE,
        DAMAGE

    }
}

*/