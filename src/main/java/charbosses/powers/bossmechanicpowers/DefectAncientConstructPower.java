//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package charbosses.powers.bossmechanicpowers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class DefectAncientConstructPower extends AbstractBossMechanicPower {
    public static final String POWER_ID = "downfall:DefectAncientConstructPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESC;

    public DefectAncientConstructPower(AbstractCreature owner, int stack) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = stack;
        this.updateDescription();
        loadRegion("curiosity");
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESC[0];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (!this.owner.hasPower(ArtifactPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new ArtifactPower(this.owner, this.amount), this.amount));
            //   AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(this.amount));
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESC = powerStrings.DESCRIPTIONS;
    }
}
