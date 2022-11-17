package champ.stances;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;

import java.util.ArrayList;
import java.util.HashMap;

import static collector.util.Wiz.atb;
import static collector.util.Wiz.makeInHand;

public abstract class AbstractChampStance extends AbstractStance {

    public static HashMap<String, Integer> stages = new HashMap<>();

    static {
        AbstractChampStance.stages.put(BerserkerStance.STANCE_ID, 0);
        AbstractChampStance.stages.put(DefensiveStance.STANCE_ID, 0);
        AbstractChampStance.stages.put(GladiatorStance.STANCE_ID, 0);
        AbstractChampStance.stages.put(UltimateStance.STANCE_ID, 0);
    }

    public static final float HEIGHT_SEQUENCE = 800f * Settings.yScale; //TODO: Merge this with auto's and put it in downfallmod
    public static final float SEQUENCED_CARD_SIZE = 0.25f;
    public static final float USED_CARD_SIZE = 0.2F;
    public static final float START_POS = 281f * Settings.xScale;
    public static final float SPACE_BETWEEN_CARDS = 100F * Settings.xScale;
    private ArrayList<AbstractCard> cards;
    private AbstractCard stancePreviewCard;
    private float previewCard_x;
    private boolean showPreviewCard = false;
    private boolean upgraded = false;

    public AbstractChampStance(String ID) {
        this.ID = ID;
        cards = getCards();
        int savedStage = stages.get(this.ID);
        for (int i = 0; i < cards.size(); i++) {
            AbstractCard c = cards.get(i);
            c.target_x = START_POS + (SPACE_BETWEEN_CARDS * i);
            c.target_y = HEIGHT_SEQUENCE;
            if (i < savedStage) {
                c.targetDrawScale = USED_CARD_SIZE;
                c.targetTransparency = 0.5F;
            } else {
                c.targetDrawScale = SEQUENCED_CARD_SIZE;
                c.targetTransparency = 1F;
            }
        }
        previewCard_x = START_POS + (SPACE_BETWEEN_CARDS * (cards.size() + 3));
    }

    @Override
    public void update() {
        super.update();
        updateCards();
    }

    private void updateCards() {
        // hover logic on cards, staggered position, etc.

        showPreviewCard = false;
        for (AbstractCard c : cards) {
            c.update();
            c.hb.update();
            if (c.hb.hovered) {
                showPreviewCard = true;
                stancePreviewCard = c.makeStatEquivalentCopy();
                stancePreviewCard.target_x = stancePreviewCard.current_x = previewCard_x;
                stancePreviewCard.target_y = stancePreviewCard.current_y = HEIGHT_SEQUENCE;
            }
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
        if (showPreviewCard) {
            stancePreviewCard.render(sb);
        }
    }

    public abstract ArrayList<AbstractCard> getCards();

    public void combo() {
        AbstractCard get = cards.get(stages.get(this.ID));
        get.targetDrawScale = USED_CARD_SIZE;
        get.targetTransparency = 0.5F;
        makeInHand(get.makeStatEquivalentCopy());
        stages.merge(this.ID, 1, Integer::sum);
        if (stages.get(this.ID) == cards.size()) {
            atb(new ChangeStanceAction(NeutralStance.STANCE_ID));
            stages.put(this.ID, 0);
            for (AbstractCard c : cards) {
                c.targetDrawScale = SEQUENCED_CARD_SIZE;
                get.targetTransparency = 1F;
            }
        }
    }

    public AbstractCard getFinalCard() {
        return cards.get(cards.size() - 1).makeStatEquivalentCopy();
    }

    public void upgradeStance() {
        if (!upgraded) {
            upgraded = true;
            cards.forEach(q -> q.upgrade());
        }
    }
}
