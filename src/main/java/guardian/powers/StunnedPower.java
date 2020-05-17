package guardian.powers;


import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.Iterator;


public class StunnedPower extends AbstractPower {
    public static final String POWER_ID = "Guardian:StunnedPower";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;

    public static String[] DESCRIPTIONS;
    public int storedHandSize;
    private AbstractCreature source;


    public StunnedPower(AbstractCreature owner) {

        this.ID = POWER_ID;
        this.owner = owner;
        this.img = ImageMaster.loadImage("images/stslib/powers/32/stun.png");
        this.type = POWER_TYPE;
        DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;
        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;

        updateDescription();

    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, StunnedPower.POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, StunnedPower.POWER_ID, 1));
        }

    }

    public void atStartOfTurn() {
        super.atStartOfTurn();
        this.storedHandSize = AbstractDungeon.player.gameHandSize;
        AbstractDungeon.player.gameHandSize = 0;
        AbstractDungeon.actionManager.cardQueue.clear();

        for (AbstractCard c : AbstractDungeon.player.limbo.group) {
            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
        }

        AbstractDungeon.player.limbo.group.clear();
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);
    }

    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        AbstractDungeon.player.gameHandSize = this.storedHandSize;
    }


    public void updateDescription() {


        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }


    }


}



