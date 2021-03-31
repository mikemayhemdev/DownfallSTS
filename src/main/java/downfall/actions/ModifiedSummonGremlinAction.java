package downfall.actions;

import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.GremlinLeader;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class ModifiedSummonGremlinAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(com.megacrit.cardcrawl.actions.unique.SummonGremlinAction.class.getName());
    private AbstractMonster m;

    public ModifiedSummonGremlinAction(AbstractMonster[] gremlins, boolean firstSummon) {
        this.actionType = ActionType.SPECIAL;
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_FAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_LONG;
        }

        this.duration = this.startDuration;
        int slot = this.identifySlot(gremlins);
        if (slot == -1) {
            logger.info("INCORRECTLY ATTEMPTED TO CHANNEL GREMLIN.");
        } else {
            if (firstSummon) {
                float x;
                float y;
                switch(slot) {
                    case 0:
                        x = GremlinLeader.POSX[0];
                        y = GremlinLeader.POSY[0];
                        break;
                    case 1:
                        x = GremlinLeader.POSX[1];
                        y = GremlinLeader.POSY[1];
                        break;
                    case 2:
                        x = GremlinLeader.POSX[2];
                        y = GremlinLeader.POSY[2];
                        break;
                    default:
                        x = GremlinLeader.POSX[0];
                        y = GremlinLeader.POSY[0];
                }
                this.m = new GremlinNob(x, y);
            } else {
                this.m = this.getRandomGremlin(slot);

            }
            gremlins[slot] = this.m;
            Iterator var3 = AbstractDungeon.player.relics.iterator();

            while (var3.hasNext()) {
                AbstractRelic r = (AbstractRelic) var3.next();
                r.onSpawnMonster(this.m);
            }
        }
    }

    private int identifySlot(AbstractMonster[] gremlins) {
        for(int i = 0; i < gremlins.length; ++i) {
            if (gremlins[i] == null || gremlins[i].isDying) {
                return i;
            }
        }

        return -1;
    }

    private AbstractMonster getRandomGremlin(int slot) {
        ArrayList<String> pool = new ArrayList();
        pool.add("GremlinWarrior");
        pool.add("GremlinWarrior");
        pool.add("GremlinThief");
        pool.add("GremlinThief");
        pool.add("GremlinFat");
        pool.add("GremlinFat");
        pool.add("GremlinTsundere");
        pool.add("GremlinWizard");
        float x;
        float y;
        switch(slot) {
            case 0:
                x = GremlinLeader.POSX[0];
                y = GremlinLeader.POSY[0];
                break;
            case 1:
                x = GremlinLeader.POSX[1];
                y = GremlinLeader.POSY[1];
                break;
            case 2:
                x = GremlinLeader.POSX[2];
                y = GremlinLeader.POSY[2];
                break;
            default:
                x = GremlinLeader.POSX[0];
                y = GremlinLeader.POSY[0];
        }

        return MonsterHelper.getGremlin((String)pool.get(AbstractDungeon.aiRng.random(0, pool.size() - 1)), x, y);
    }

    private int getSmartPosition() {
        int position = 0;

        for(Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); var2.hasNext(); ++position) {
            AbstractMonster mo = (AbstractMonster)var2.next();
            if (this.m.drawX <= mo.drawX) {
                break;
            }
        }

        return position;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            this.m.animX = 1200.0F * Settings.xScale;
            this.m.init();
            this.m.applyPowers();
            AbstractDungeon.getCurrRoom().monsters.addMonster(this.getSmartPosition(), this.m);
            if (ModHelper.isModEnabled("Lethality")) {
                this.addToBot(new ApplyPowerAction(this.m, this.m, new StrengthPower(this.m, 3), 3));
            }

            if (ModHelper.isModEnabled("Time Dilation")) {
                this.addToBot(new ApplyPowerAction(this.m, this.m, new SlowPower(this.m, 0)));
            }

            if (!(this.m instanceof GremlinNob)) {
                this.addToBot(new ApplyPowerAction(this.m, this.m, new MinionPower(this.m)));
            }
        }

        this.tickDuration();
        if (this.isDone) {
            this.m.animX = 0.0F;
            this.m.showHealthBar();
            this.m.usePreBattleAction();
        } else {
            this.m.animX = Interpolation.fade.apply(0.0F, 1200.0F * Settings.xScale, this.duration);
        }

    }
}
