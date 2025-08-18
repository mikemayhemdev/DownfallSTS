package awakenedOne.actions;

import awakenedOne.cards.tokens.spells.ESPSpell;
import awakenedOne.powers.IntensifyDebuffPower;
import awakenedOne.relics.ZenerDeck;
import awakenedOne.ui.OrbitingSpells;
import awakenedOne.util.OnConjureSubscriber;
import awakenedOne.util.Wiz;
import charbosses.cards.colorless.EnApotheosis;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import java.util.ArrayList;

import static awakenedOne.AwakenedOneMod.UP_NEXT;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.spells;
import static awakenedOne.util.Wiz.isAwakened;
import static downfall.downfallMod.DeterministicConjure;

public class ConjureAction extends AbstractGameAction {

    public static boolean refreshedthisturn = false;
    public static ArrayList<AbstractCard> conjuredCards = new ArrayList();
    public static int conjuresThisCombat = 0;
    private final boolean choose;
    private final boolean ontop;
    private final boolean bstudy;
    private int permaAdd;
    private int extraCreate;
    AbstractCard pick;

    public ConjureAction(boolean choose) {
        this.choose = choose;
        ontop = false;
        bstudy = false;
        pick = null;
        permaAdd = 0;
        extraCreate = 0;
    }

    public ConjureAction(boolean choose, int permaAddCount, int creationCount) {
        this.choose = choose;
        ontop = false;
        bstudy = false;
        pick = null;
        permaAdd = permaAddCount;
        extraCreate = creationCount;
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
        if (AbstractDungeon.player.hasPower(IntensifyDebuffPower.POWER_ID)) {
            AbstractDungeon.player.getPower(IntensifyDebuffPower.POWER_ID).flash();
            this.isDone = true;
            return;
        }
        if (AbstractDungeon.player.hasPower("No Draw")) {
            AbstractDungeon.player.getPower("No Draw").flash();
            this.isDone = true;
            return;
        }
        conjuresThisCombat += 1;
        isDone = true;
        conjuredCards.clear();
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if ((spellCards.isEmpty())) {
                    awaken(5);
                    OrbitingSpells.refreshSpells();
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
            AbstractCard tar = new EnApotheosis(); //dummy card
            if (!bstudy) {
                if (!DeterministicConjure) {
                    tar = Wiz.getRandomItem(spellCards, AbstractDungeon.cardRandomRng).makeStatEquivalentCopy();
                }
                if (DeterministicConjure) {
                    for (AbstractCard c : spellCards) {
                        System.out.println("DEBUG: CHECKING: " + c.name);
                        if (c.hasTag(UP_NEXT)) {
                            System.out.println("DEBUG: YES! UP NEXT: " + c.name);
                            tar = c.makeStatEquivalentCopy();
                        }
                    }
                }
            }
            System.out.println("DEBUG: TARGET CARD: " + tar.name);
            if (tar instanceof EnApotheosis) {
                tar = spellCards.get(0);
            }

            if (bstudy) {
                tar = pick;
            }
            if (isAwakened()) {
                tar.upgrade();
            }
            if (!ontop) {
                addToTop(new MakeTempCardInHandAction(tar));
            }
            if (ontop) {
                addToTop(new MakeTempCardInDrawPileAction(tar, 1, false, true));
            }
            if (!bstudy) {
                addToTop(new RemoveSpellCardAction(tar));
            } else {
                addToTop(new RemoveSpellCardActionSpecial(tar));
            }
        } else {
            if (permaAdd == 0) {
                if (AbstractDungeon.player.hasPower(IntensifyDebuffPower.POWER_ID)) {
                    AbstractDungeon.player.getPower(IntensifyDebuffPower.POWER_ID).flash();
                    this.isDone = true;
                    return;
                }
                if (AbstractDungeon.player.hasPower("No Draw")) {
                    AbstractDungeon.player.getPower("No Draw").flash();
                    this.isDone = true;
                    return;
                }
            }
            ArrayList<AbstractCard> possCards = new ArrayList<>();
            ArrayList<AbstractCard> availableCards = new ArrayList<>();
            ArrayList<AbstractCard> actualChoices = new ArrayList<>();
            if (permaAdd == 0){
                possCards.addAll(spellCards);
                while (!possCards.isEmpty()) {
                    availableCards.add(possCards.remove(AbstractDungeon.cardRandomRng.random(possCards.size() - 1)));
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    availableCards.add(CardLibrary.getCard(spells.get(i)));
                }
                if (AbstractDungeon.player.hasRelic(ZenerDeck.ID)) {
                    actualChoices.add(CardLibrary.getCard(ESPSpell.ID));
                }
            }
            availableCards.forEach(q -> actualChoices.add(q.makeStatEquivalentCopy()));
            addToTop(new SelectCardsCenteredAction(actualChoices, "", (cards) -> {
                AbstractCard q = cards.get(0);
                if (isAwakened()) {
                    q.upgrade();
                }
                addToTop(new MakeTempCardInHandAction(q));
                if (permaAdd == 0){
                    addToTop(new RemoveSpellCardAction(q));
                } else {
                    for (int i = 0; i < extraCreate-1; i++) {
                        addToTop(new MakeTempCardInHandAction(q));
                    }
                    for (int i = 0; i < permaAdd; i++) {
                        spellCards.add(q.makeStatEquivalentCopy());
                    }
                }
            }));
        }
        if (permaAdd == 0) {
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if (c instanceof OnConjureSubscriber) ((OnConjureSubscriber) c).OnConjure();
            }
        }
    }
}