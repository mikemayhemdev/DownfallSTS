package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import hermit.patches.EnumPatch;

import java.util.Iterator;

public class FinalCanterAction extends AbstractGameAction {
    private AbstractCreature m;
    private AbstractPlayer p;
    private AbstractCard c;

    public FinalCanterAction(AbstractCreature m, AbstractPlayer p, int amount, AbstractCard c) {
        this.m = m;
        this.p = p;
        this.c = c;
    }

    public void update() {
        this.c.calculateCardDamage((AbstractMonster)this.m);
        this.addToTop(new DamageAction(this.m, new DamageInfo(this.p, this.c.damage, DamageInfo.DamageType.NORMAL), EnumPatch.HERMIT_GHOSTFIRE));

        this.isDone = true;
    }
}