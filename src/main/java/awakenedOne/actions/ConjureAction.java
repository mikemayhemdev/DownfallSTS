package awakenedOne.actions;

import awakenedOne.cards.HeavyStrike;
import awakenedOne.powers.EmpressPower;
import awakenedOne.powers.FeathersinksPower;
import awakenedOne.ui.OrbitingSpells;
import awakenedOne.util.OnConjureSubscriber;
import awakenedOne.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theHexaghost.util.OnAdvanceOrRetractSubscriber;

import java.util.ArrayList;

public class ConjureAction extends AbstractGameAction {
    private final AbstractGameAction followUpAction;
    public static ArrayList<AbstractCard> conjuredCards = new ArrayList();

    public static int conjuresThisCombat = 0;
    public static boolean refreshedthisturn = false;
    private boolean choose;

    public ConjureAction(boolean choose) {
        this(choose, null);
    }

    public ConjureAction(boolean choose, AbstractGameAction action) {
        this.choose = choose;
        followUpAction = action;
    }

    @Override
    public void update() {
        conjuresThisCombat += 1;
        isDone = true;
        conjuredCards.clear();
        if (OrbitingSpells.spellCards.size() == 1) {
            addToTop(new RefreshSpellsAction());
            refreshedthisturn = true;
            //On Refresh...
            if (AbstractDungeon.player.hasPower(FeathersinksPower.POWER_ID)) {
                for (int i = 0; i < AbstractDungeon.player.getPower(FeathersinksPower.POWER_ID).amount; i++) {
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(1));
                }
            }
        }
            if (!choose) {
                AbstractCard tar = Wiz.getRandomItem(OrbitingSpells.spellCards, AbstractDungeon.cardRandomRng).card.makeStatEquivalentCopy();
                conjuredCards.add(tar);
                endActionWithFollowUp();
                if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
                    double buf = AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
                    if (buf > 10) {
                        tar.upgrade();
                    }
                }
                addToTop(new MakeTempCardInHandAction(tar));
                addToTop(new RemoveSpellCardAction(tar));
            } else {
                ArrayList<OrbitingSpells.CardRenderInfo> possCards = new ArrayList<>();
                possCards.addAll(OrbitingSpells.spellCards);
                ArrayList<OrbitingSpells.CardRenderInfo> availableCards = new ArrayList<>();
                while (!possCards.isEmpty()) {
                    availableCards.add(possCards.remove(AbstractDungeon.cardRandomRng.random(possCards.size() - 1)));
                }
                ArrayList<AbstractCard> actualChoices = new ArrayList<>();
                availableCards.forEach(q -> actualChoices.add(q.card.makeStatEquivalentCopy()));
                addToTop(new SelectCardsCenteredAction(actualChoices, "Choose a Spell to add into your hand.", (cards) -> {
                    AbstractCard q = cards.get(0);
                    conjuredCards.add(q);
                    endActionWithFollowUp();
                    if (AbstractDungeon.player.hasPower(EmpressPower.POWER_ID)) {
                        q.upgrade();
                    }
                    addToTop(new MakeTempCardInHandAction(q));
                    addToTop(new RemoveSpellCardAction(q));
                }));
            }
        }

    private void endActionWithFollowUp() {
        if (this.followUpAction != null) {
            this.addToTop(this.followUpAction);
        }
    }
}
