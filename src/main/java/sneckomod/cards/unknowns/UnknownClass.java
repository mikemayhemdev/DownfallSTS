/*
package sneckomod.cards.unknowns;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import sneckomod.CardIgnore;

import java.util.ArrayList;
import java.util.function.Predicate;

@CardIgnore
public class UnknownClass extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownClass");

    public UnknownClass(CardColor cardColor) {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON);
        myColor = cardColor;
    }


    public String getCharName(CardColor myColor) {
        ArrayList<AbstractPlayer> theDudes = new ArrayList<AbstractPlayer>(CardCrawlGame.characterManager.getAllCharacters());
        for (AbstractPlayer p : theDudes) {
            if (p.getCardColor() == myColor)
                return p.getLocalizedCharacterName();
        }
        return "You should never see this. Report to Vex";
    }


    private CardColor myColor;

    @Override
    public AbstractCard makeCopy() {
        return new UnknownClass(myColor);
    }

    @Override
    public void update() {
        super.update();
        if (!this.rawDescription.equals("sneckomod:Unknown" + getCharName(myColor) + ".")) {
            this.rawDescription = "sneckomod:Unknown" + getCharName(myColor) + ".";
            initializeDescription();
        }
    }

    public static CardColor getRandomCardColor() {
        ArrayList<CardColor> myList = new ArrayList<>(BaseMod.getCardColors());
        return myList.get(AbstractDungeon.cardRandomRng.random(myList.size() - 1));
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.color == myColor;
    }
}

*/