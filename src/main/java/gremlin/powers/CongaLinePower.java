package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.GremlinMod;
import gremlin.actions.GremlinSwapAction;

public class CongaLinePower extends AbstractGremlinPower {
    public static final String POWER_ID = getID("CongaLine");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/conga_line.png"));

    public CongaLinePower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(strings.DESCRIPTIONS[0]);
        for (int i = 0; i < this.amount; i++) {
            sb.append("[E] ");
        }
        sb.append(strings.DESCRIPTIONS[1]);
        this.description = sb.toString();
    }

    @Override
    public void atStartOfTurn()
    {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction());
        flash();
    }
}

