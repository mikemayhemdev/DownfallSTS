package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.BuffSecondarySlimeEffectsPower;

public class CommandAction extends AbstractGameAction {

    public void update() {
        isDone = true;
        AbstractOrb oldestOrb = SlimeboundMod.getLeadingSlime();
        if (oldestOrb != null) {
            addToTop(new TrigggerSpecificSlimeAttackAction(oldestOrb));
            if (AbstractDungeon.player.hasPower(BuffSecondarySlimeEffectsPower.POWER_ID)) {
                for (int i = 0; i < AbstractDungeon.player.getPower(BuffSecondarySlimeEffectsPower.POWER_ID).amount; i++) {
                    addToTop(new TrigggerSpecificSlimeAttackAction(oldestOrb));
                }
            }
        }
    }
}

