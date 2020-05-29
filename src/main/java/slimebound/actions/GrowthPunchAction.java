package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.cards.GrowthPunch;
import slimebound.powers.TackleModifyDamagePower;

public class GrowthPunchAction extends AbstractGameAction {

    private GrowthPunch card;
    private int growth;
    public GrowthPunchAction(GrowthPunch c, int value) {
        this.card = c;
        this.growth = value;
    }

    @Override
    public void update() {
        card.baseDamage += this.growth;
        card.baseBlock += this.growth;
        isDone = true;
    }
}
