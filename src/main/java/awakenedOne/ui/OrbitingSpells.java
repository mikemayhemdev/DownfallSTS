package awakenedOne.ui;

import awakenedOne.cards.AphoticFount;
import awakenedOne.cards.Deathwish;
import awakenedOne.cards.tokens.spells.*;
import awakenedOne.relics.ZenerDeck;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;

import java.util.ArrayList;

public class OrbitingSpells {

    private static final float X_DIST = 300F;
    private static final float Y_DIST = 80F;
    private static final float DIST_BETWEEN_CARDS = MathUtils.PI2;
    private static final float TIME_MULT = MathUtils.PI2 * 0.075F;
    private static final float SPELL_SIZE = 0.5F;
    private static final float SPELL_SIZE_MOD = 0.166F;
    private static final float SPELL_TRANSPARENCY = 0.5F;

    private static final ArrayList<String> spells = new ArrayList<>();

    public static ArrayList<CardRenderInfo> spellCards = new ArrayList<>();
    public static float time;

    static {
        spells.add(BurningStudy.ID);
        spells.add(Thunderbolt.ID);
        spells.add(Darkleech.ID);
        spells.add(Cryostasis.ID);
    }

    public static void refreshSpells() {
        spellCards.clear();
        for (int i = 0; i < spells.size(); i++) {
            addSpellCard(CardLibrary.getCard(spells.get(i)).makeCopy());
        }

        int count = (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(q -> q instanceof Deathwish).count();
        for(int i=0; i<count; i++) {
            addSpellCard(CardLibrary.getCard(DeathCoil.ID).makeCopy());
        }

        int count2 = (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(q -> q instanceof AphoticFount).count();
        for(int i=0; i<count2; i++) {
            addSpellCard(CardLibrary.getCard(AphoticShield.ID).makeCopy());
        }

        if (AbstractDungeon.player.hasRelic(ZenerDeck.ID)) {
            addSpellCard(CardLibrary.getCard(ESPSpell.ID).makeCopy());
        }

    }

    public static void upgradeall() {
        for (CardRenderInfo c : spellCards) {
            c.card.upgrade();
        }
    }

    public static void empty() {
        spellCards.clear();
        update();
    }


    public static void addSpellCard(AbstractCard card) {
        if (AbstractDungeon.player.hasPower(MasterRealityPower.POWER_ID)) {
            card.upgrade();
        }
        spellCards.add(new CardRenderInfo(card));
        updateTimeOffsets();
    }

    public static boolean removeSpellCard(AbstractCard card) {
        int idx = getIndexOfCard(card);
        if (idx != -1) {
            spellCards.remove(getIndexOfCard(card));
            updateTimeOffsets();
            return true;
        }
        return false;
    }

    public static void updateTimeOffsets() {
        for (int i = 0; i < spellCards.size(); i++) {
            spellCards.get(i).timeOffset = ((float) i / (float) spellCards.size()) * DIST_BETWEEN_CARDS;
        }
    }

    public static void atBattleStart() {
        refreshSpells();
    }

    public static void update() {
        time += Gdx.graphics.getDeltaTime() * TIME_MULT;
        for (CardRenderInfo c : spellCards) {
            c.updatePositions();
            c.card.update();
        }
    }

    public static void prePlayerRender(SpriteBatch sb) {
        for (CardRenderInfo c : spellCards) {
            if (c.renderBehind) {
                c.card.render(sb);
            }
        }
    }

    public static void postPlayerRender(SpriteBatch sb) {
        for (CardRenderInfo c : spellCards) {
            if (!c.renderBehind) {
                c.card.render(sb);
            }
        }
    }

    public static int getIndexOfCard(AbstractCard card) {
        for (int i = 0; i < spellCards.size(); i++) {
            if (spellCards.get(i).card.cardID.equals(card.cardID)) {
                return i;
            }
        }
        return -1;
    }

    public static class CardRenderInfo {
        public boolean renderBehind;
        private float timeOffset;
        public AbstractCard card;

        public CardRenderInfo(AbstractCard card) {
            card.targetTransparency = card.transparency = SPELL_TRANSPARENCY;
            this.card = card;
        }

        public void updatePositions() {
            card.target_x = (float) (X_DIST * Math.cos(time + timeOffset)) + (AbstractDungeon.player.drawX + (AbstractDungeon.player.hb.width / 5));
            card.target_y = (float) (Y_DIST * Math.sin(time + timeOffset)) + (AbstractDungeon.player.drawY + (AbstractDungeon.player.hb.height / 2));
            boolean isBehind = ((time + timeOffset) / MathUtils.PI) % 2 < 1;
            renderBehind = isBehind;
            float adjustedTime1 = (time + timeOffset) % MathUtils.PI;
            float adjustedTime2 = ((time + timeOffset) % (MathUtils.PI / 2));
            if (adjustedTime1 / (MathUtils.PI / 2) >= 1) {
                adjustedTime2 = (MathUtils.PI / 2) - adjustedTime2;
            }
            float sizeMod = ((adjustedTime2 / (MathUtils.PI / 2)) * SPELL_SIZE_MOD);
            card.targetDrawScale = SPELL_SIZE + (sizeMod * (isBehind ? -1 : 1));
        }
    }
}
