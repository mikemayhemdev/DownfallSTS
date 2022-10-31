package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import hermit.patches.EnumPatch;

public class MagnumActionDamage extends AbstractGameAction {
    private AbstractCreature m;
    private AbstractPlayer p;
    private AbstractCard c;

    public MagnumActionDamage(AbstractCreature m, AbstractPlayer p, AbstractCard c) {
        this.m = m;
        this.p = p;
        this.c = c;
    }

    public void update() {
        this.c.calculateCardDamage((AbstractMonster)this.m);
        this.addToTop(new DamageAction(this.m, new DamageInfo(this.p, this.c.damage, DamageInfo.DamageType.NORMAL), EnumPatch.HERMIT_GUN));

        this.isDone = true;
    }
}