package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.GremlinMod;
import gremlin.relics.WizardStaff;

public class WizPower extends AbstractGremlinPower {
    public static final String POWER_ID = getID("Wiz");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/wiz.png"));

    public WizPower(AbstractCreature owner, int amount) {
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
        if(this.amount < 3) {
            if(this.amount == 2) {
                this.description = (strings.DESCRIPTIONS[0] + (3 - this.amount) + strings.DESCRIPTIONS[1]);
            }
            else {
                this.description = (strings.DESCRIPTIONS[0] + (3 - this.amount) + strings.DESCRIPTIONS[2]);
            }
        } else {
            this.description = strings.DESCRIPTIONS[3];
        }
    }

    @Override
    public void onInitialApplication() {
        if(amount >= 3){
            if(AbstractDungeon.player.hasRelic(WizardStaff.ID))
            {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                        new BangPower(this.owner, 10 + WizardStaff.OOMPH), 1));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                        new BangPower(this.owner, 10), 1));
            }
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(amount >= 3){
            if(!this.owner.hasPower(BangPower.POWER_ID)) {
                if(AbstractDungeon.player.hasRelic(WizardStaff.ID))
                {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                            new BangPower(this.owner, 10 + WizardStaff.OOMPH), 1));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
                            new BangPower(this.owner, 10), 1));
                }
            }
        }
    }

    @Override
    public void onRemove() {
        if(this.owner.hasPower(EncorePower.POWER_ID)){
            this.owner.getPower(EncorePower.POWER_ID).onSpecificTrigger();
        }
    }
}

