package awakenedOne.actions;

import awakenedOne.cards.tokens.spells.BurningStudy;
import awakenedOne.powers.DarkIncantationRitualPower;
import awakenedOne.powers.FeathersinksPower;
import awakenedOne.powers.RisingChantPower;
import awakenedOne.ui.OrbitingSpells;
import awakenedOne.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RitualPower;

import java.util.ArrayList;

import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.applyToSelf;

public class ConjureAction extends AbstractGameAction {


    private boolean choose;
    private boolean ontop;
    private boolean bstudy;
    AbstractCard pick;

    public static boolean refreshedthisturn = false;
    public static ArrayList<AbstractCard> conjuredCards = new ArrayList();

    public static int conjuresThisCombat = 0;


    public ConjureAction(boolean choose) {
        this.choose = choose;
        ontop = false;
        bstudy = false;
        pick = null;
    }

    public ConjureAction(boolean choose, boolean drawpile) {
        this.choose = choose;
        ontop = drawpile;
        bstudy = false;
        pick = null;
    }

    public ConjureAction(boolean choose, boolean drawpile, boolean starterrelic, AbstractCard summon) {
        this.choose = choose;
        ontop = drawpile;
        bstudy = starterrelic;
        pick = summon;
    }

    @Override
    public void update() {
        conjuresThisCombat += 1;
        isDone = true;
        conjuredCards.clear();
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    if ((OrbitingSpells.spellCards.isEmpty())) {
                        awaken(5);
                        OrbitingSpells.refreshSpells();

                        for (OrbitingSpells.CardRenderInfo c : OrbitingSpells.spellCards) {
                            c.card.upgrade();
                        }

                        if (AbstractDungeon.player.hasPower(DarkIncantationRitualPower.POWER_ID) && refreshedthisturn == false) {
                            applyToSelf(new RitualPower(AbstractDungeon.player, AbstractDungeon.player.getPower(DarkIncantationRitualPower.POWER_ID).amount, true));
                            AbstractDungeon.player.getPower(DarkIncantationRitualPower.POWER_ID).onSpecificTrigger();
                        }

                        //On Refresh...
//                        if (AbstractDungeon.player.hasPower(FeathersinksPower.POWER_ID)) {
//                            for (int i = 0; i < AbstractDungeon.player.getPower(FeathersinksPower.POWER_ID).amount; i++) {
//                                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
//                            }
//                        }
                        refreshedthisturn = true;
                    }
                }
            });
            if (!choose) {
                AbstractCard tar = Wiz.getRandomItem(OrbitingSpells.spellCards, AbstractDungeon.cardRandomRng).card.makeStatEquivalentCopy();
                if (bstudy) {
                    tar = pick;
                }
                if (ontop == false) {
                    addToTop(new MakeTempCardInHandAction(tar));
                }
                if (ontop == true) {
                    addToBot(new MakeTempCardInDrawPileAction(tar, 1, false, true));
                }
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
                addToTop(new SelectCardsCenteredAction(actualChoices, "", (cards) -> {
                    AbstractCard q = cards.get(0);
                    addToTop(new MakeTempCardInHandAction(q));
                    addToTop(new RemoveSpellCardAction(q));
                }));
            }
    }
}