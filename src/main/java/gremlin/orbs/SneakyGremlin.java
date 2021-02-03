package gremlin.orbs;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.powers.GremlinPower;
import gremlin.powers.SneakyGremlinPower;

public class SneakyGremlin extends GremlinStandby{

    public SneakyGremlin(int hp) {
        super(hp, "Gremlin:SneakyGremlin", "sneak", "animation", 25);
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + 3 + this.descriptions[1];
    }

    @Override
    public AbstractOrb makeCopy() {
        return new SneakyGremlin(hp);
    }

    @Override
    public void playChannelSFX() {

    }

    @Override
    public GremlinPower getPower() {
        return new SneakyGremlinPower(3);
    }
}
