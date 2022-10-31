package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import gremlin.GremlinMod;

public class UnforgivingPower extends AbstractGremlinPower{
    public static final String POWER_ID = getID("Unforgiving");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/unforgiving.png"));

    public UnforgivingPower(AbstractCreature owner) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = -1;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = strings.DESCRIPTIONS[0];
    }
}

