package champ.stances;

import champ.ChampChar;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.stances.AbstractStance;

import java.util.ArrayList;

public abstract class AbstractChampStance extends AbstractStance {

    public static final float HEIGHT_SEQUENCE = 800f * Settings.yScale; //TODO: Merge this with auto's and put it in downfallmod
    public static final float START_POS = 281f * Settings.xScale;
    public static final float SPACE_BETWEEN_CARDS = 75F * Settings.xScale;
    private ArrayList<AbstractCard> cards;

    public String STANCE_ID = "guardianmod:AbstractMode";

    public AbstractChampStance() {
        this.ID = STANCE_ID;
        this.name = ChampChar.characterStrings.TEXT[3];
        this.updateDescription();
        cards = getCards();
        for (int i = 0; i < cards.size(); i++) {
            AbstractCard c = cards.get(i);
            c.target_x = START_POS + (SPACE_BETWEEN_CARDS * i);
            c.target_y = HEIGHT_SEQUENCE;
        }
    }

    @Override
    public void update() {
        super.update();
        updateCards();
    }

    private void updateCards() {
        // hover logic on cards, staggered position, etc.

        for (AbstractCard c : cards) {
            c.update();
            c.updateHoverLogic();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        renderCards(sb);
    }

    private void renderCards(SpriteBatch sb) {
        // for cards, render card

        sb.setColor(Color.WHITE.cpy());
        for (AbstractCard c : cards) {
            c.render(sb);
        }
    }


    public abstract String getName();

    public void updateDescription() {
        this.description = "temp!"; //TODO: Description code
    }

    public abstract ArrayList<AbstractCard> getCards();
}
