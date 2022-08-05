package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import hermit.patches.EnumPatch;

public class FromBeyondAction extends AbstractGameAction {
    private AbstractCard card;
    private int damage;

    public FromBeyondAction(AbstractCard card, int damage) {
        this.card = card;
        this.damage = damage;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.addToTop(new FastLoseHPAction(this.target, AbstractDungeon.player, damage, EnumPatch.HERMIT_GHOSTFIRE));
        }

        this.isDone = true;
    }
}
