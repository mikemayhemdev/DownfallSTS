package automaton.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class TinkerersToolbox extends AbstractBronzeCard {

    public final static String ID = makeID("TinkerersToolbox");

    //stupid intellij stuff skill, self, rare

    public static AbstractCard constPrev1 = new Debug();
    public static AbstractCard constPrev2 = new Batch();
    public static AbstractCard constPrev3 = new Decompile();

    public AbstractCard prev1 = null;
    public AbstractCard prev2 = null;
    public AbstractCard prev3 = null;

    public TinkerersToolbox() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        exhaust = true;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> myCardsList = new ArrayList<>();
        myCardsList.add(new Debug());
        myCardsList.add(new Batch());
        myCardsList.add(new Decompile());
        addToBot(new SelectCardsAction(myCardsList, 1, "Choose.", (cards) -> addToTop(new MakeTempCardInHandAction(cards.get(0).makeCopy(), true))));
    }

    public void upp() {
        selfRetain = true;
    }

    @Override
    public void hover() {
        prev1 = constPrev1;
        prev2 = constPrev2;
        prev3 = constPrev3;
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
    public void renderCardTip(SpriteBatch sb) {
        super.renderCardTip(sb);
        if (!hb.hovered || isLocked || (AbstractDungeon.player != null && (AbstractDungeon.player.isDraggingCard || AbstractDungeon.player.inSingleTargetMode))) {
            return;
        }

        float drawScale = 0.7f;
        float yPosition1 = this.current_y + this.hb.height * 1.2f;
        float yPosition2 = this.current_y + this.hb.height * .6F;
        float yPosition3 = this.current_y;

        //changes the Arcana preview to render below the Arcana in the shop so it doesn't clip out of the screen
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.SHOP) {
            yPosition1 = this.current_y - this.hb.height * 1.2f;
            yPosition2 = this.current_y - this.hb.height * .6F;
            yPosition3 = this.current_y;
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
                card.current_x = xPosition1;
                card.current_y = yPosition2;
                card.render(sb);
            }
        }
        if (prev3 != null) {
            AbstractCard card = prev3.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition1;
                card.current_y = yPosition3;
                card.render(sb);
            }
        }
    }
}