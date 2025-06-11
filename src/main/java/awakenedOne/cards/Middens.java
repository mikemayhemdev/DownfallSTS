package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;

import java.util.ArrayList;

public class Middens extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Middens.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public static final String[] TEXT;

    public Middens() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();

        ArrayList<AbstractCard> qCardList = new ArrayList<>();

        boolean multipletypes = false;
        AbstractCard checker = null;

        for (AbstractCard c : p.discardPile.group) {
            if (c.type == CardType.STATUS) {
                qCardList.add(c);
                checker = c;
            }
        }

        for (AbstractCard c : qCardList) {
            if (c.name != checker.name) {
                multipletypes = true;
            }
        }



        if (!multipletypes) {
            ArrayList<AbstractCard> syntheticSockets = new ArrayList<>();
            syntheticSockets.addAll(qCardList);

            if (!syntheticSockets.isEmpty()) {
                int vibe = magicNumber;
                if (vibe > syntheticSockets.size()) {
                    vibe = syntheticSockets.size();
                    if (!syntheticSockets.isEmpty()) {
                        for (int i = 0; i < this.magicNumber; i++) {
                            if (!syntheticSockets.isEmpty()) {
                                if (p.hand.size() < BaseMod.MAX_HAND_SIZE) {
                                    AbstractCard selecteda = syntheticSockets.get((AbstractDungeon.cardRandomRng.random(syntheticSockets.size() - 1)));
                                    p.discardPile.removeCard(selecteda);
                                    p.hand.addToHand(selecteda);
                                    selecteda.lighten(false);
                                    selecteda.unhover();
                                    selecteda.applyPowers();
                                    syntheticSockets.remove(selecteda);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (multipletypes) {
            ArrayList<AbstractCard> syntheticSockets = new ArrayList<>();
            syntheticSockets.addAll(qCardList);

            if (!syntheticSockets.isEmpty()) {
                int vibe = magicNumber;
                if (vibe > syntheticSockets.size()) {
                    vibe = syntheticSockets.size();
                }

                this.addToTop(new SelectCardsCenteredAction(
                        syntheticSockets,
                        vibe,
                        TEXT[0],
                        (selectedCards) -> {
                            if (!syntheticSockets.isEmpty()) {
                                for (int i = 0; i < this.magicNumber; i++) {
                                    if (!syntheticSockets.isEmpty()) {
                                        if (p.hand.size() < BaseMod.MAX_HAND_SIZE) {
                                            AbstractCard selecteda = selectedCards.get(i);
                                            p.discardPile.removeCard(selecteda);
                                            p.hand.addToHand(selecteda);
                                            selecteda.lighten(false);
                                            selecteda.unhover();
                                            selecteda.applyPowers();
                                            syntheticSockets.remove(selecteda);
                                        }
                                    }
                                }
                            }
                        }
                ));
            }
        }

    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}