package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import gremlin.GremlinMod;
import gremlin.actions.RandomGremlinSwapAction;

public class ScatterPower extends AbstractGremlinPower {
    static final String POWER_ID = getID("Scatter");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/scatter.png"));

    public ScatterPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        if(this.amount == 1) {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
        }
        else {
            this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[2]);
        }
    }

    public int onLoseHp(int damageAmount)
    {
        if (damageAmount > 0) {
            AbstractDungeon.actionManager.addToTop(new RandomGremlinSwapAction());
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
        return 0;
    }
}

