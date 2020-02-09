package charbosses.actions.unique;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class EnemyArmamentsAction extends AbstractGameAction {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
        TEXT = EnemyArmamentsAction.uiStrings.TEXT;
    }

    private AbstractCharBoss p;
    private ArrayList<AbstractCard> cannotUpgrade;
    private boolean upgraded;

    public EnemyArmamentsAction(final boolean armamentsPlus) {
        this.cannotUpgrade = new ArrayList<AbstractCard>();
        this.upgraded = false;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractCharBoss.boss;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = armamentsPlus;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.upgraded) {
                for (final AbstractCard c : this.p.hand.group) {
                    if (c.canUpgrade()) {
                        c.upgrade();
                        c.superFlash();
                        c.applyPowers();
                    }
                }
                this.isDone = true;
                return;
            }
            for (final AbstractCard c : this.p.hand.group) {
                if (!c.canUpgrade()) {
                    this.cannotUpgrade.add(c);
                }
            }
            if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }
            for (final AbstractCard c : this.p.hand.group) {
                if (c.canUpgrade()) {
                    c.upgrade();
                    c.superFlash();
                    c.applyPowers();
                    this.isDone = true;
                    return;
                }
            }
        }
        this.tickDuration();
    }
}
