//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package slimebound.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import slimebound.orbs.SpawnedSlime;
import slimebound.powers.PotencyPower;

import java.util.ArrayList;

public class CoordinateAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private int damage;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageType damageTypeForTurn;
    private int energyOnUse = -1;
    private int slimesToTrigger = 0;
    private int block;

    public CoordinateAction(AbstractPlayer p, AbstractMonster m, int slimesToTrigger, boolean freeToPlayOnce, int energyOnUse, int block) {
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.slimesToTrigger = slimesToTrigger;
        this.energyOnUse = energyOnUse;
        this.block = block;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {


            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new PotencyPower(p, p, effect), effect));

        ArrayList<AbstractOrb> oldestOrb = new ArrayList<>();
        for (AbstractOrb o : p.orbs) {
            if (o instanceof SpawnedSlime) {
                oldestOrb.add(o);
                if (oldestOrb.size() == this.slimesToTrigger) break;
            }

        }
        for (int i = 0; i < effect; ++i) {

            if (oldestOrb.size() > 0) {
                for (AbstractOrb o : oldestOrb) {
                    com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new TrigggerSpecificSlimeAttackAction(o));


                }
            }
            //   com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F * effect));


            //com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new EvokeSpecificOrbAction(oldestOrb));
        }

        this.isDone = true;
    }
}
