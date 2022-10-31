package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.GremlinMod;

public class HecklePower extends AbstractGremlinPower {
    public static final String POWER_ID = getID("Heckle");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/heckle.png"));

    public HecklePower(AbstractCreature owner, int amount) {
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
        this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if ((power.type == AbstractPower.PowerType.DEBUFF) && (!power.ID.equals("Shackled")) && (source == this.owner) && (target != this.owner) &&
                (!target.hasPower("Artifact")))
        {
            flash();
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(owner, owner, amount));
        }
    }
}

