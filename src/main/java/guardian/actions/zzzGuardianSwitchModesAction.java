package guardian.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import guardian.powers.zzzGuardianModePower;


public class zzzGuardianSwitchModesAction extends AbstractGameAction {
    private boolean toDefensive;
    private boolean toShattered;
    private AbstractPlayer p;

    public zzzGuardianSwitchModesAction(AbstractPlayer p, boolean toDefensive, boolean toShattered) {
        this.toDefensive = toDefensive;
        this.toShattered = toShattered;
        this.p = p;
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {

        if (p.hasPower(zzzGuardianModePower.POWER_ID)) {
            zzzGuardianModePower pG = (zzzGuardianModePower) p.getPower(zzzGuardianModePower.POWER_ID);

            if (toDefensive) {
                if (!pG.inDefensive) {
                    pG.switchToDefensiveMode();
                }
            } else {
                if (pG.inDefensive) {
                    pG.switchToOffensiveMode();
                }
            }

        } else {
            AbstractPower newPower = new zzzGuardianModePower(p);
            if (this.toDefensive) {
                ((zzzGuardianModePower) newPower).switchToDefensiveMode();
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new zzzGuardianModePower(p)));
        }

        this.isDone = true;
    }
}
