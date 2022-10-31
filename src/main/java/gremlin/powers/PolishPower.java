package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.GremlinMod;
import gremlin.cards.Ward;

public class PolishPower extends AbstractGremlinPower {
    public static final String POWER_ID = getID("Polish");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/polish.png"));

    public PolishPower(AbstractCreature owner, int amount) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = amount;
        this.updateDescription();
        updateWardsInHand();
    }

    public void updateDescription()
    {
        this.description = strings.DESCRIPTIONS[0] + this.amount + strings.DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount)
    {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateWardsInHand();
    }

    private void updateWardsInHand()
    {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if ((c instanceof Ward)) {
                if (!c.upgraded) {
                    c.baseBlock = (Ward.BLOCK + this.amount);
                } else {
                    c.baseBlock = (Ward.BLOCK + Ward.UPGRADE_BONUS + this.amount);
                }
            }
        }
    }

    public void onDrawOrDiscard()
    {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if ((c instanceof Ward)) {
                if (!c.upgraded) {
                    c.baseBlock = (Ward.BLOCK + this.amount);
                } else {
                    c.baseBlock = (Ward.BLOCK + Ward.UPGRADE_BONUS + this.amount);
                }
            }
        }
    }
}

