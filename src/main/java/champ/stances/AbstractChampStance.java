package champ.stances;

import champ.ChampChar;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.ArrayList;

public abstract class AbstractChampStance extends AbstractStance {

    public static final float HEIGHT_SEQUENCE = 800f * Settings.yScale; //TODO: Merge this with auto's and put it in downfallmod
    private ArrayList<AbstractCard> cards;

    public String STANCE_ID = "guardianmod:AbstractMode";

    public AbstractChampStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[3];
        this.updateDescription();
        cards = getCards();
    }

    @Override
    public void update() {
        super.update();
        updateCards();
    }

    private void updateCards() {
        // hover logic on cards, etc.

        for (AbstractCard q : cards) {

        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        renderCards(sb);
    }

    private void renderCards(SpriteBatch sb) {
        // for cards, render card, staggered offset.
    }


    public abstract String getName();

    public void updateDescription() {
        this.description = "temp!"; //TODO: Description code
    }

    public abstract ArrayList<AbstractCard> getCards();
}
