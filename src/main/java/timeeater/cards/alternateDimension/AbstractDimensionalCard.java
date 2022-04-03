package timeeater.cards.alternateDimension;

import automaton.cards.AbstractBronzeCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.TimeEaterChar;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;

public abstract class AbstractDimensionalCard extends AbstractTimeEaterCard {
    public final static String ID = makeID("PackRat");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,


    public AbstractDimensionalCard(final String cardID, final int cost, final CardType type, final CardTarget target) {
        super(cardID, cost, type, CardRarity.SPECIAL, target, TimeEaterChar.Enums.MAGENTA);
    }

    public void setFrame(String img){
        this.setBackgroundTexture("timeResources/images/512/" + img, "timeResources/images/1024/" + img);
    }


    @Override
    protected Texture getPortraitImage() {
        return null;
    }

    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {

        }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upp() {
    }
}