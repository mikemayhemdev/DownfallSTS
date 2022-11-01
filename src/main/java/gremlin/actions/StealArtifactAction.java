package gremlin.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class StealArtifactAction extends AbstractGameAction {
    public StealArtifactAction(final AbstractCreature target, AbstractCreature source) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.BLOCK;
        this.target = target;
        this.source = source;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.target != null && this.target.hasPower("Artifact")) {
            amount = this.target.getPower("Artifact").amount;
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(source, source,
                    new ArtifactPower(source, amount), amount));
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(target, source, "Artifact"));
        }
        this.tickDuration();
    }
}
