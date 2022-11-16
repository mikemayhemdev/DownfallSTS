//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package collector.torchhead;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PetTorch extends AbstractMonster {

    private int damage;

    public PetTorch(float offsetX, float offsetY) {
        super("Torch Head (needs loc)", "collector:PetTorch", 35, -5.0F, -20.0F, 145.0F, 240.0F, null, offsetX, offsetY, true);
        this.loadAnimation("images/monsters/theCity/torchHead/skeleton.atlas", "images/monsters/theCity/torchHead/skeleton.json", 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        flipHorizontal = true;
        healthBarUpdatedEvent();
    }

    @Override
    public void render(SpriteBatch sb) {
        this.state.update(Gdx.graphics.getDeltaTime());
        this.state.apply(this.skeleton);
        this.skeleton.updateWorldTransform();
        this.skeleton.setPosition(this.drawX + this.animX, this.drawY + this.animY);
        this.skeleton.setColor(this.tint.color);
        this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, this.skeleton);
        CardCrawlGame.psb.end();
        sb.begin();
        sb.setBlendFunction(770, 771);
        this.hb.render(sb);
        this.intentHb.render(sb);
        this.healthHb.render(sb);
        boolean oldHideCombatElements = Settings.hideCombatElements;
        Settings.hideCombatElements = true;
        renderHealth(sb);
        Settings.hideCombatElements = oldHideCombatElements;
        ReflectionHacks.privateMethod(AbstractMonster.class, "renderName", SpriteBatch.class).invoke(this, sb);
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
