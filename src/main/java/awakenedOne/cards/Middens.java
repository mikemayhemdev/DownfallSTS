package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.util.SelectCardsCenteredAction;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

import java.util.ArrayList;
import java.util.Collections;

public class Middens extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Middens.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    boolean chant = false;
    public static final String[] TEXT;

    public Middens() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 3;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();

        ArrayList<AbstractCard> qCardList = new ArrayList<>();

        for (AbstractCard c : p.discardPile.group) {
            if (c.type == CardType.STATUS) {
                qCardList.add(c);
            }
        }

            ArrayList<AbstractCard> syntheticSockets = new ArrayList<>();
            syntheticSockets.addAll(qCardList);

            if (!syntheticSockets.isEmpty()) {
                this.addToTop(new SelectCardsCenteredAction(

                        syntheticSockets,
                        1,
                        TEXT[0],
                        (selectedCards) -> {
                            if (!syntheticSockets.isEmpty()) {
                                AbstractCard selecteda = selectedCards.get(0);
                                    p.discardPile.removeCard(selectedCards.get(0));
                                    p.hand.addToHand(selectedCards.get(0));
                                    selectedCards.get(0).lighten(false);
                                    selectedCards.get(0).unhover();
                                    selectedCards.get(0).applyPowers();
                                }
                        }
                ));
            }
        }

    @Override
    public void upp() {
        upgradeBlock(3);
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}