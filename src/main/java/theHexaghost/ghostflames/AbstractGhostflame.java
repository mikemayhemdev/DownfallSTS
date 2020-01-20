package theHexaghost.ghostflames;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import theHexaghost.GhostflameHelper;
import theHexaghost.relics.SpiritBrand;
import theHexaghost.vfx.MyOrb;

public abstract class AbstractGhostflame {
    public int damage = -1;
    public int block = -1;
    public int magic = -1;

    public boolean charged = false;

    public MyOrb graphicalRender;

    public Hitbox hitbox;

    public void charge() {
        graphicalRender.charge();
        if (AbstractDungeon.player.hasRelic(SpiritBrand.ID)) {
            AbstractDungeon.player.getRelic(SpiritBrand.ID).flash();
            atb(new GainBlockAction(AbstractDungeon.player, 3));
        }
        charged = true;
        onCharge();
    }

    public abstract void onCharge();

    public float lx;
    public float ly;

    public AbstractGhostflame(float x, float y) {
        lx = x;
        ly = y;
        hitbox = new Hitbox(x, y, 64, 64);
        graphicalRender = new MyOrb(hitbox.cX, hitbox.cY, this);
    }

    public void update() {
        graphicalRender.update();
        hitbox.update();
    }

    public abstract String getDescription();

    public void atb(AbstractGameAction e) {
        AbstractDungeon.actionManager.addToBottom(e);
    }

    public void extinguish() {
        graphicalRender.charged = false;
        charged = false;
    }

    public void enhance() {
        if (damage > -1) damage++;
        if (block > -1) block++;
        if (magic > -1) magic++;
    }

    public void activate() {
        GhostflameHelper.activeGhostFlame = this;
    }
}
