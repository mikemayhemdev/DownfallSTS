/*
package sneckomod.cards.unknowns;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.function.Predicate;

public class UnknownClass extends AbstractUnknownCard {
    public final static String ID = makeID("UnknownClass");

    public UnknownClass() {
        super(ID, CardType.SKILL, CardRarity.UNCOMMON, getRandomCardColor());
    }

    @Override
    public void update() {
        super.update();
        this.rawDescription = "sneckomod:Unknown" + color.name() + ".";
    }

    public static CardColor getRandomCardColor() {
        ArrayList<CardColor> myList = new ArrayList<>(BaseMod.getCardColors());
        return myList.get(AbstractDungeon.cardRandomRng.random(myList.size() - 1));
    }

    @Override
    public Predicate<AbstractCard> myNeeds() {
        return c -> c.color == this.color;
    }
}

*/