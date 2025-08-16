package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.patches.EnumPatch;

public class FinalCanterAction extends AbstractGameAction {
    private final AbstractCreature m;
    private final AbstractPlayer p;
    private final AbstractCard c;
    private AttackEffect eff = EnumPatch.HERMIT_GHOSTFIRE;

    public FinalCanterAction(AbstractCreature m, AbstractPlayer p, int amount, AbstractCard c, AttackEffect eff) {
        this.m = m;
        this.p = p;
        this.c = c;
        this.eff = eff;
    }

    public FinalCanterAction(AbstractCreature m, AbstractPlayer p, int amount, AbstractCard c) {
        this.m = m;
        this.p = p;
        this.c = c;
    }

    public void update() {
        this.c.calculateCardDamage((AbstractMonster)this.m);
        this.addToTop(new DamageAction(this.m, new DamageInfo(this.p, this.c.damage, DamageInfo.DamageType.NORMAL), this.eff));

        this.isDone = true;
    }
}