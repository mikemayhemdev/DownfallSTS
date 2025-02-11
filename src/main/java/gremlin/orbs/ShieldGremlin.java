package gremlin.orbs;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.powers.GremlinPower;
import gremlin.powers.ShieldGremlinPower;

public class ShieldGremlin extends GremlinStandby{
    public static final int BLOCK = 2;

    public ShieldGremlin(int hp) {
        super(hp, "Gremlin:ShieldGremlin", "shield", "idle", 25);
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + BLOCK + this.descriptions[1];
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ShieldGremlin(hp);
    }

    @Override
    public void playChannelSFX() {

    }

    @Override
    public GremlinPower getPower() {
        return new ShieldGremlinPower(BLOCK);
    }
}

