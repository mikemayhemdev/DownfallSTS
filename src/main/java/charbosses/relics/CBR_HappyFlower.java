package charbosses.relics;

import charbosses.actions.common.EnemyGainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.HappyFlower;

public class CBR_HappyFlower extends AbstractCharbossRelic {
    public static final String ID = "HappyFlower";

    public CBR_HappyFlower() {
        super(new HappyFlower());
    }

    @Override
    public String getUpdatedDescription() {
        if (this.owner != null) {
            return this.setDescription(this.owner.chosenClass);
        }
        return this.setDescription(null);
    }

    private String setDescription(final AbstractPlayer.PlayerClass c) {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1];
    }

    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = this.setDescription(c);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_HappyFlower();
    }

    @Override
    public void onEquip() {
        this.counter = 1;
    }

    @Override
    public void onEnergyRecharge() {
        if (this.counter == -1) {
            this.counter += 2;
        } else {
            ++this.counter;
        }
        if (this.counter == 3) {
            this.counter = 0;
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(this.owner, this));
            this.addToBot(new EnemyGainEnergyAction(this.owner, 1));
        }
    }

}
