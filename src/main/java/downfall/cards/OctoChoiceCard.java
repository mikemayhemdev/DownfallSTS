package downfall.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.util.CardIgnore;

@CardIgnore
public class OctoChoiceCard extends CustomCard {
    private static final int COST = -2;
    private String IMG = null;

    private AbstractCard card1;
    private AbstractCard card2;
    private AbstractCard card3;

    private AbstractCard prev1;
    private AbstractCard prev2;
    private AbstractCard prev3;

    public OctoChoiceCard(String id, String name, String IMG, String description) {
        super(id, name, IMG, COST, description, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.IMG = IMG;
    }

    public OctoChoiceCard(String id, String name, String IMG, String description, AbstractCard prev1, AbstractCard prev2, AbstractCard prev3) {
        super(id, name, IMG, COST, description, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
        this.IMG = IMG;
    }

    @Override
    public void hover() {
        if (card1 != null){
            prev1 = card1;
        }
        if (card2 != null){
            prev2 = card2;
        }
        if (card3 != null){
            prev3 = card3;
        }
        super.hover();
    }

    @Override
    public void unhover() {
        super.unhover();
        prev1 = null;
        prev2 = null;
        prev3 = null;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }

    @Override
    public void renderCardTip(SpriteBatch sb) {
        super.renderCardTip(sb);

        //Removes the preview when the player is manipulating the card or if the card is locked
        if (isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode))) {
            return;
        }

        float drawScale = 0.5f;
        float yPosition1 = this.current_y + this.hb.height * 0.75f;
        float yPosition2 = this.current_y + this.hb.height * 0.25f;
        float yPosition3 = this.current_y - this.hb.height * 0.25f;

        //changes the Arcana preview to render below the Arcana in the shop so it doesn't clip out of the screen
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
            yPosition1 = this.current_y - this.hb.height * 0.75f;
            yPosition2 = this.current_y - this.hb.height * 0.25f;
            yPosition3 = this.current_y + this.hb.height * 0.25f;
        }

        float xPosition1;
        float xPosition2;
        float xPosition3;
        float xOffset1 = -this.hb.width * 0.75f;
        float xOffset2 = -this.hb.width * 0.25f;
        float xOffset3 = this.hb.width * 0.25f;

        //inverts the x position if the card is a certain amount to the right to prevent clipping issues
        if (this.current_x > Settings.WIDTH * 0.75F) {
            xOffset1 = -xOffset1;
            xOffset2 = -xOffset2;
            xOffset3 = -xOffset3;
        }

        xPosition1 = this.current_x + xOffset1;
        xPosition2 = this.current_x + xOffset2;
        xPosition3 = this.current_x + xOffset3;

        if (prev1 != null) {
            AbstractCard card = prev1.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition1;
                card.current_y = yPosition1;
                card.render(sb);
            }
        }
        if (prev2 != null) {
            AbstractCard card = prev2.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition2;
                card.current_y = yPosition2;
                card.render(sb);
            }
        }
        if (prev3 != null) {
            AbstractCard card = prev3.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition3;
                card.current_y = yPosition3;
                card.render(sb);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new OctoChoiceCard(cardID, name, IMG, rawDescription);
    }
}