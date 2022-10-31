package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import gremlin.GremlinMod;

public class BashedPower extends AbstractGremlinPower implements OnPlayerGainBlockPower {
    static final String POWER_ID = getID("Bashed");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/bashed.png"));

    public BashedPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.DEBUFF;
        this.amount = amount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = (strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1]);
    }

    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public float onPlayerGainBlock(float amount) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, null, this.amount));
        return amount;
    }
}

