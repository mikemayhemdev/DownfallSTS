//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package collector.torchhead;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PetTorch extends AbstractMonster {

    public PetTorch(float offsetX, float offsetY) {
        super("Torch Head (needs loc)", "collector:PetTorch", 35, -5.0F, -20.0F, 145.0F, 240.0F, null, offsetX, offsetY, true);
        this.loadAnimation("images/monsters/theCity/torchHead/skeleton.atlas", "images/monsters/theCity/torchHead/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public boolean hasPower(String targetID) {
        return false;
    }

    @Override
    public AbstractPower getPower(String targetID) {
        return null;
    }

    @Override
    public void addPower(AbstractPower powerToApply) {
    }

    @Override
    public void takeTurn() {
    }

    @Override
    protected void getMove(int i) {
    }
}
