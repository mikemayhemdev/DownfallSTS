package charbosses.monsters;

import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class MirrorImageSilent extends AbstractMonster {

    public static final String ID = downfallMod.makeID("MirrorImageSilent");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Silent").NAMES[0];
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 150.0F;
    private static final float HB_H = 320.0F;

    public MirrorImageSilent() {
        super(NAME, ID, 1, HB_X, HB_Y, HB_W, HB_H, null, 200, -50, false);
        type = EnemyType.NORMAL; //????
    }

    @Override
    public void render(SpriteBatch sb) {
        AbstractCharBoss c = AbstractCharBoss.boss;
        if (c != null) {
            float origX = c.drawX;
            float origY = c.drawY;
            float origHbX = c.hb.x;
            float origHbY = c.hb.y;
            c.drawX = this.drawX;
            c.drawY = this.drawY;
            c.hb.x = this.hb.x;
            c.hb.y = this.hb.y;
            ((AbstractMonster) c).render(sb);
            c.drawX = origX;
            c.drawY = origY;
            c.hb.x = origHbX;
            c.hb.y = origHbY;
        }
    }

    @Override
    public void renderTip(SpriteBatch sb) {
        AbstractCharBoss c = AbstractCharBoss.boss;
        if (c != null) {
            float origX = c.hb.x;
            float origY = c.hb.y;
            c.hb.x = this.hb.x;
            c.hb.y = this.hb.y;
            ((AbstractMonster) c).renderTip(sb);
            c.drawX = origX;
            c.drawY = origY;
        }
    }

    @Override
    public void takeTurn() {
        //Maybe she should like, laugh at you for not figuring it out?
    }

    protected void getMove(int num) {
        this.setMove((byte)0, Intent.NONE);  // This is irrelevant!
    }

}
