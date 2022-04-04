package timeeater.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsCenteredAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.Grudge;
import hermit.cards.LuckOfTheDraw;
import hermit.cards.Malice;
import timeeater.TimeEaterMod;
import timeeater.cards.alternateDimension.*;
import timeeater.util.Wiz;

import java.util.ArrayList;
import java.util.Collections;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.att;

public class DimensionGate extends AbstractTimeEaterCard {
    public final static String ID = makeID("DimensionGate");
    // intellij stuff  uncommon,


    public DimensionGate() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsCenteredAction(cards(), cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            att(new MakeTempCardInHandAction(cards.get(0)));
        }));
    }

    protected ArrayList<AbstractCard> cards() {
        if (TimeEaterMod.dimensionCards.size() == 0)
            TimeEaterMod.initializeDimensionCardStrings();
        ArrayList<AbstractCard> retVal = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            retVal.add(CardLibrary.getCard(Wiz.getRandomItem(TimeEaterMod.dimensionCards)).makeCopy());
        }
        return retVal;
    }

    @Override
    public void upp() {
        upgradeBaseCost(0);
    }
}