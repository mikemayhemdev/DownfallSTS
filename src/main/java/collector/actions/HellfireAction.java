package collector.actions;

import collector.CollectorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.FlameAnimationEffect;

public class HellfireAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int damage;
    private AbstractPlayer p;
    private DamageType damageTypeForTurn;
    private int energyOnUse;
    private boolean Upgraded;

    public HellfireAction(AbstractPlayer p, int damage, DamageType damageTypeForTurn, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.p = p;
        this.damage = damage;
        Upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.damageTypeForTurn = damageTypeForTurn;
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
            addToBot(new VFXAction(new FlameAnimationEffect(p.hb)));
            for(int i = 0; i < effect; ++i) {
                AbstractMonster m = AbstractDungeon.getRandomMonster();
                this.addToBot(new DamageAction(m, new DamageInfo(this.p, this.damage, this.damageTypeForTurn), AttackEffect.FIRE));
                this.addToBot(new ApplyPowerAction(m,p,CollectorMod.GetRandomAffliction(m,Upgraded)));
            }

            if (!this.freeToPlayOnce) {
                AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
