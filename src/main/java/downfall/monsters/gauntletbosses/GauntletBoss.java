package downfall.monsters.gauntletbosses;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.monsters.NeowBoss;

public abstract class GauntletBoss extends AbstractMonster {

    public GauntletBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    @Override
    public void die() {
        super.die();
        boolean shouldEnd = true;
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (q instanceof GauntletBoss && q != this) {
                if (!q.isDead && !q.isDying) {
                    shouldEnd = false;
                }
            }
        }
        if (shouldEnd) {
            NeowBoss.neowboss.takeTurn();
        }
    }
}
