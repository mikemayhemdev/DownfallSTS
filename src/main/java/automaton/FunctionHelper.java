package automaton;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class FunctionHelper {
    public static AbstractCard held1;
    public static AbstractCard held2;
    public static AbstractCard held3;
    public static AbstractCard held4;

    public AbstractCard makeFunction() {
        return new Shiv(); //TODO: Make this do the thing
    }
}
