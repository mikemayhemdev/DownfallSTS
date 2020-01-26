package slimebound.powers;


import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;

import java.util.Iterator;


public class StunnedPower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:StunnedPower";
    public static final String NAME = "Potency";
    public static PowerType POWER_TYPE = PowerType.DEBUFF;
    public static final String IMG = "powers/SleepSmall.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());

    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    public int storedHandSize;


    public StunnedPower(AbstractCreature owner, AbstractCreature source, int amount) {

        this.name = NAME;

        this.ID = POWER_ID;


        this.owner = owner;

        this.source = source;


        this.img = new com.badlogic.gdx.graphics.Texture(SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.amount = amount;
        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

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
        Iterator var1 = AbstractDungeon.player.limbo.group.iterator();

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
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



