package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import slimebound.powers.SlimedPower;


public class SlimesplosionAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private int damage;
    private AbstractPlayer p;
    private AbstractMonster m;
    private DamageType damageTypeForTurn;
    private int energyOnUse = -1;
    private int slimed = 0;

    private int poison = 0;


    public SlimesplosionAction(AbstractPlayer p, int slimed, int poison, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.m = m;

        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.DAMAGE;

        this.slimed = slimed;
        this.poison = poison;
        this.energyOnUse = energyOnUse;
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
            for (int i = 0; i < effect; i++) {
                for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                    if ((!monster.isDead) && (!monster.isDying)) {

                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new SlimedPower(monster, p, this.slimed), this.slimed, true, AbstractGameAction.AttackEffect.NONE));
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new PoisonPower(monster, p, this.poison), this.poison, true, AbstractGameAction.AttackEffect.NONE));


                    }
                }
            }


            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }
}
