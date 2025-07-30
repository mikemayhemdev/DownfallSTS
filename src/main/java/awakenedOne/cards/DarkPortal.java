package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MultiGroupSelectAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.SelectCardsCenteredAction;

import java.util.Collections;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class DarkPortal extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(DarkPortal.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public static final String[] TEXT;

    public DarkPortal() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(DarkPortal.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new MultiGroupSelectAction(
                cardStrings.EXTENDED_DESCRIPTION[magicNumber == 1 ? 0 : 1],
                (cards, groups) -> {
                    Collections.reverse(cards);
                    cards.forEach(c -> att(new AbstractGameAction() {
                        public void update() {
                            isDone = true;
                            if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                                if (groups.get(c) == p.drawPile)
                                    p.drawPile.moveToDiscardPile(c);
                                p.createHandIsFullDialog();
                            } else {
                                p.hand.moveToHand(c, groups.get(c));
                            }
                            if (p.drawPile.contains(cards.get(0))) {
                                addToTop(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
                            } else if (p.discardPile.contains(cards.get(0))) {
                                addToTop(new MakeTempCardInDiscardAction(new VoidCard(), 1));
                            }
                        }
                    }));
                },
                magicNumber, false, c -> c instanceof AbstractCard, CardGroup.CardGroupType.DRAW_PILE, CardGroup.CardGroupType.DISCARD_PILE
        ));
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT;
    }
}