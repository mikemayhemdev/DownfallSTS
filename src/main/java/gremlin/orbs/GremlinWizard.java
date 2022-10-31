package gremlin.orbs;

import com.megacrit.cardcrawl.orbs.AbstractOrb;
import gremlin.powers.GremlinPower;
import gremlin.powers.GremlinWizardPower;

public class GremlinWizard extends GremlinStandby{

    public GremlinWizard(int hp) {
        super(hp,"Gremlin:GremlinWizard", "wizard", "animation", 25);
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + 1 + this.descriptions[1];
    }

    @Override
    public AbstractOrb makeCopy() {
        return new GremlinWizard(hp);
    }

    @Override
    public void playChannelSFX() {

    }

    @Override
    public GremlinPower getPower() {
        return new GremlinWizardPower(1);
    }
}
