package theHexaghost.ghostflames;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theHexaghost.GhostflameHelper;
import theHexaghost.relics.SpiritBrand;
import theHexaghost.util.OnChargeSubscriber;
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
        charged = true;
        onCharge();
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof OnChargeSubscriber) ((OnChargeSubscriber) p).onCharge(this);
        }
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (r instanceof OnChargeSubscriber) ((OnChargeSubscriber) r).onCharge(this);
        }
    }

    public abstract void onCharge();

    public float lx;
    public float ly;

    public AbstractGhostflame(float x, float y) {
        lx = x;
        ly = y;
        hitbox = new Hitbox(x, y, 64, 64);
        graphicalRender = new MyOrb(x, y, this, hitbox);
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
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);// 297
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);// 298
    }

    public void activate() {
        GhostflameHelper.activeGhostFlame = this;
    }
}
