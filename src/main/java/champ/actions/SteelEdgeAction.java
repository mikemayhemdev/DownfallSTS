package champ.actions;

import champ.cards.AbstractChampCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.AbstractXAction;

public class SteelEdgeAction extends AbstractXAction {

    private int bonusAmt;
    private int boom;
    private int boom2;

    public SteelEdgeAction(int x, int y, int bonusAmt, AbstractMonster target) {
        this.bonusAmt = bonusAmt;
        boom = x;
        boom2 = y;
        this.target = target;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void initialize(int totalAmount) {
        super.initialize(totalAmount);
        this.amount += bonusAmt;
    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (AbstractChampCard.bcombo())
            for (int i = 0; i < amount; i++) {
                addToTop(new DamageAction(target, new DamageInfo(p, boom, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_HORIZONTAL));
            }
        if (AbstractChampCard.dcombo())
            for (int i = 0; i < amount; i++) {
                addToTop(new GainBlockAction(p, boom2));
            }
        isDone = true;
    }
}