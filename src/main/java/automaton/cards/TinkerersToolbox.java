package automaton.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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

        float drawScale = 0.5f;
        float yPosition1 = Settings.HEIGHT * 0.2F;

        float xPosition1 = Settings.WIDTH * 0.35F;
        float xPosition2 = Settings.WIDTH * 0.5F;
        float xPosition3 = Settings.WIDTH * 0.65F;

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
                card.current_y = yPosition1;
                card.render(sb);
            }
        }
        if (prev3 != null) {
            AbstractCard card = prev3.makeStatEquivalentCopy();
            if (card != null) {
                card.drawScale = drawScale;
                card.current_x = xPosition3;
                card.current_y = yPosition1;
                card.render(sb);
            }
        }
    }
}