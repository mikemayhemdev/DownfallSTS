package gremlin.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import gremlin.GremlinMod;

public class CrippledPower extends AbstractGremlinPower {
    public static final String POWER_ID = getID("Crippled");
    private static final PowerStrings strings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final Texture IMG = new Texture(GremlinMod.getResourcePath("powers/crippled.png"));

    public CrippledPower(AbstractCreature owner) {
        this.name = strings.NAME;
        this.ID = POWER_ID;
        this.owner = owner;

        this.img = IMG;
        this.type = PowerType.DEBUFF;
        this.amount = -1;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = strings.DESCRIPTIONS[0];
    }
}

