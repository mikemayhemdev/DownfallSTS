package charbosses.vfx;

import charbosses.bosses.Ironclad.CharBossIronclad;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.Iterator;

public class EnemyReaperEffect extends AbstractGameEffect {
    public EnemyReaperEffect() {
    }

    public void update() {
        CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
        CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);

        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && !m.id.equals(CharBossIronclad.ID)) {
                AbstractDungeon.effectsQueue.add(new LightningEffect(m.hb.cX, m.hb.cY));
            }
        }

        AbstractDungeon.effectsQueue.add(new LightningEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));

        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
