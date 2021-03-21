package gremlin.orbs;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.powers.FatGremlinPower;
import gremlin.powers.GremlinPower;

public class FatGremlin extends GremlinStandby{

    public FatGremlin(int hp) {
        super(hp, "Gremlin:FatGremlin", "fat", "animation", 25);
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + 1 + this.descriptions[1];
    }

    @Override
    public AbstractOrb makeCopy() {
        return new FatGremlin(hp);
    }

    @Override
    public void playChannelSFX() {

    }

    @Override
    public GremlinPower getPower() {
        return new FatGremlinPower(1);
    }
}
