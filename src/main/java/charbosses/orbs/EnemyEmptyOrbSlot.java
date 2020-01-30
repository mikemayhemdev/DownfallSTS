package charbosses.orbs;

import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import charbosses.bosses.AbstractCharBoss;

import com.badlogic.gdx.math.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.core.*;

public class EnemyEmptyOrbSlot extends AbstractEnemyOrb
{
    public static final String ORB_ID = "Empty";
    private static final OrbStrings orbString;
    public static final String[] DESC;
    
    public EnemyEmptyOrbSlot(final float x, final float y) {
        this.angle = MathUtils.random(360.0f);
        this.ID = "Empty";
        this.name = EnemyEmptyOrbSlot.orbString.NAME;
        this.evokeAmount = 0;
        this.cX = x;
        this.cY = y;
        this.updateDescription();
        this.channelAnimTimer = 0.5f;
    }
    
    public EnemyEmptyOrbSlot() {
        this.angle = MathUtils.random(360.0f);
        this.name = EnemyEmptyOrbSlot.orbString.NAME;
        this.evokeAmount = 0;
        this.cX = AbstractCharBoss.boss.drawX + AbstractCharBoss.boss.hb_x;
        this.cY = AbstractCharBoss.boss.drawY + AbstractCharBoss.boss.hb_y + AbstractCharBoss.boss.hb_h / 2.0f;
        this.updateDescription();
    }
    
    @Override
    public void updateDescription() {
        this.description = EnemyEmptyOrbSlot.DESC[0];
    }
    
    @Override
    public void onEvoke() {
    }
    
    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 10.0f;
    }
    
    @Override
    public void render(final SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(ImageMaster.ORB_SLOT_2, this.cX - 48.0f - this.bobEffect.y / 8.0f, this.cY - 48.0f + this.bobEffect.y / 8.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, 0.0f, 0, 0, 96, 96, false, false);
        sb.draw(ImageMaster.ORB_SLOT_1, this.cX - 48.0f + this.bobEffect.y / 8.0f, this.cY - 48.0f - this.bobEffect.y / 8.0f, 48.0f, 48.0f, 96.0f, 96.0f, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }
    
    @Override
    public AbstractOrb makeCopy() {
        return new EnemyEmptyOrbSlot();
    }
    
    @Override
    public void playChannelSFX() {
    }
    
    static {
        orbString = CardCrawlGame.languagePack.getOrbString("Empty");
        DESC = EnemyEmptyOrbSlot.orbString.DESCRIPTION;
    }
}
