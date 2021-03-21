package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.GremlinMod;
import gremlin.cards.Ward;

public class InfiniteBlocksPower extends AbstractGremlinPower {
    public static final String POWER_ID = getID("InfiniteBlocks");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/infinite_blocks.png"));

    public InfiniteBlocksPower(AbstractCreature owner, int amount) {
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
        if(this.amount == 1) {
            this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1];
        } else {
            this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[2];
        }
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Ward(), amount, false));
        }
    }
}

