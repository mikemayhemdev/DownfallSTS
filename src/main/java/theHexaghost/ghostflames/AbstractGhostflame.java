package theHexaghost.ghostflames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theHexaghost.GhostflameHelper;
import theHexaghost.TheHexaghost;
import theHexaghost.actions.GreenFlameAction;
import theHexaghost.util.OnChargeSubscriber;
import theHexaghost.vfx.MyOrb;

public abstract class AbstractGhostflame {
    public int damage = -1;
    public int block = -1;
    public int magic = -1;

    public boolean charged = false;

    public MyOrb graphicalRender;

    public Hitbox hitbox;
    public float lx;
    public float ly;
    public float flashTimer = 1.0F;

    public AbstractGhostflame(float x, float y) {
        lx = x;
        ly = y;
        hitbox = new Hitbox(x, y, 80 * Settings.scale, 80 * Settings.scale);
        graphicalRender = new MyOrb(x, y, this, hitbox);
    }

    public void charge() {
        if (!charged) {
            flash();
            charged = true;
            atb(new GreenFlameAction(graphicalRender));
            onCharge();
            for (AbstractPower p : AbstractDungeon.player.powers) {
                if (p instanceof OnChargeSubscriber) ((OnChargeSubscriber) p).onCharge(this);
            }
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof OnChargeSubscriber) ((OnChargeSubscriber) r).onCharge(this);
            }
            if (AbstractDungeon.player instanceof TheHexaghost) {
                int x = 0;
                for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames)
                    if (gf.charged) x++;
                ((TheHexaghost) AbstractDungeon.player).myBody.targetRotationSpeed = 100F + (20 * x);
            }
            reset();
        }
    }

    public abstract void onCharge();

    public void update() {
        graphicalRender.update();
        hitbox.update();
        if (flashTimer > 1.0F)
            flashTimer -= Gdx.graphics.getDeltaTime();
        if (flashTimer < 1.0F)
            flashTimer = 1.0F;
    }

    public abstract Texture getHelperTexture();

    public abstract String getName();

    public abstract String getDescription();

    public abstract String returnHoverHelperText();

    public void atb(AbstractGameAction e) {
        AbstractDungeon.actionManager.addToBottom(e);
    }

    public void att(AbstractGameAction e) {
        AbstractDungeon.actionManager.addToTop(e);
    }

    public void extinguish() {
        graphicalRender.charged = false;
        charged = false;
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);// 297
        CardCrawlGame.sound.play("CARD_EXHAUST", 0.2F);// 298
        reset();
        if (AbstractDungeon.player instanceof TheHexaghost) {
            int x = 0;
            for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames)
                if (gf.charged) x++;
            ((TheHexaghost) AbstractDungeon.player).myBody.targetRotationSpeed = 100F + (20 * x);
        }
    }

    public void flash() {
        if (!this.charged) flashTimer = 1.5F;
    }

    public void reset() {

    }

    public void activate() {
        GhostflameHelper.activeGhostFlame = this;
    }
}
