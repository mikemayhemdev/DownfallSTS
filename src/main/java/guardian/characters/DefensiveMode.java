package guardian.characters;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.stances.AbstractStance;
import guardian.powers.DefensiveModeBooster;
import guardian.powers.DontLeaveDefensiveModePower;
import guardian.relics.DefensiveModeMoreBlock;

public class DefensiveMode extends AbstractStance {

    public static final String STANCE_ID = "guardianmod:DefensiveMode";

    public DefensiveMode() {
        this.ID = STANCE_ID;// 21
        this.name = GuardianCharacter.charStrings.TEXT[4];
        this.updateDescription();// 23
    }// 24

    @Override
    public void onEnterStance() {
        if (AbstractDungeon.player instanceof GuardianCharacter) {
            ((GuardianCharacter) AbstractDungeon.player).switchToDefensiveMode();
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, 3), 3));
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof DefensiveModeBooster) {
                ((DefensiveModeBooster) p).onEnter();
            }
        }
    }

    @Override
    public void onExitStance() {
        if (AbstractDungeon.player instanceof GuardianCharacter) {
            ((GuardianCharacter) AbstractDungeon.player).switchToOffensiveMode();
        }
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, ThornsPower.POWER_ID, 3));
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof DefensiveModeBooster) {
                ((DefensiveModeBooster) p).onLeave();
            }
        }
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        int block = 2;
        if (AbstractDungeon.player.hasRelic(DefensiveModeMoreBlock.ID)) block++;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.player.hasPower(DontLeaveDefensiveModePower.POWER_ID)) {
            AbstractDungeon.player.getPower(DontLeaveDefensiveModePower.POWER_ID).flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, DontLeaveDefensiveModePower.POWER_ID, 1));
        } else
            AbstractDungeon.actionManager.addToBottom(new ChangeStanceAction("Neutral"));// 49
    }// 50

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 0.75F;
        } else {
            return damage;
        }
    }

    @Override
    public void updateDescription() {
        this.description = GuardianCharacter.charStrings.TEXT[5];
    }
}
