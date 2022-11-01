package hermit.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import hermit.patches.EnumPatch;

public class FromBeyondAction extends AbstractGameAction {
    private final AbstractCard card;
    private final int damage;

    public FromBeyondAction(AbstractCard card, int damage) {
        this.card = card;
        this.damage = damage;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.addToTop(new FastLoseHPAction(this.target, AbstractDungeon.player, damage, EnumPatch.HERMIT_GHOSTFIRE));
        }

        this.isDone = true;
    }
}
