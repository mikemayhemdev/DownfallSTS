package awakenedOne.ui;

import awakenedOne.AwakenedOneChar;
import awakenedOne.actions.ConjureAction;
import awakenedOne.actions.SetUpNextSpellAction;
import awakenedOne.cards.Caw;
import awakenedOne.cards.tokens.spells.*;
import awakenedOne.relics.ZenerDeck;
import awakenedOne.util.TexLoader;
import awakenedOne.util.Wiz;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;
import java.util.HashMap;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.*;
import static downfall.downfallMod.DeterministicConjure;

public class OrbitingSpells {

    public static final float POSITION_X = 95F * Settings.scale;
    public static final float POSITION_Y = 300F * Settings.scale;
    public static final float PANEL_BG_X = POSITION_X - (66F * Settings.scale);
    public static final float PANEL_BG_Y = Settings.HEIGHT - POSITION_Y - 370F * Settings.scale;//POSITION_Y;//Settings.HEIGHT - (POSITION_Y);// (-110F * Settings.scale));
    public static final ArrayList<String> spells = new ArrayList<>();
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Spellbook"));
    private static final HashMap<String, Texture> cardIcons = new HashMap<>();
    private static final HashMap<String, Color> cardColors = new HashMap<>();
    private static final Hitbox barBox = new Hitbox(POSITION_X - 75F * Settings.scale, Settings.HEIGHT - POSITION_Y - 350 * Settings.scale, 55 * Settings.scale, 55 * Settings.scale * 7);
    private static final Texture unfilledPip = TexLoader.getTexture("awakenedResources/images/ui/pip_unfilled.png");
    private static final Texture pipFillAnim1 = TexLoader.getTexture("awakenedResources/images/ui/pip_unfilled.png");
    private static final Texture pipFillAnim2 = TexLoader.getTexture("awakenedResources/images/ui/pip_unfilled_3.png");
    private static final Texture filledPip = TexLoader.getTexture("awakenedResources/images/ui/pip_filled.png");
    private static final Texture pipComplete = TexLoader.getTexture("awakenedResources/images/ui/pip_complete.png");
    private static final Texture panelBG = TexLoader.getTexture("awakenedResources/images/ui/panelBg.png");
    private static final Color defaultNextColor = Color.GREEN.cpy();
    public static ArrayList<AbstractCard> spellCards = new ArrayList<>();
    public static ArrayList<Hitbox> boxes = new ArrayList<>();
    private static int hoveredCard = -1;

    private static final HashMap<Integer, Float> barSlotAnimTimers = new HashMap<>();

    static {
        for (int i = 0; i < 10; i++) {
            boxes.add(new Hitbox(POSITION_X - (5F * Settings.scale), Settings.HEIGHT - (POSITION_Y + (i * (70F * Settings.scale))) - 35 * Settings.scale, 200F * Settings.scale, 45F * Settings.scale));
        }

        cardIcons.put(BurningStudy.ID, TexLoader.getTexture("awakenedResources/images/ui/BurningStudy.png"));
        cardIcons.put(Cryostasis.ID, TexLoader.getTexture("awakenedResources/images/ui/Cryostasis.png"));
        cardIcons.put(Darkleech.ID, TexLoader.getTexture("awakenedResources/images/ui/Darkleech.png"));
        cardIcons.put(Thunderbolt.ID, TexLoader.getTexture("awakenedResources/images/ui/Thunderbolt.png"));
        //cardIcons.put(DeathCoil.ID, TexLoader.getTexture("awakenedResources/images/ui/Deathcoil.png"));
        //cardIcons.put(AphoticShield.ID, TexLoader.getTexture("awakenedResources/images/ui/AphoticShield.png"));
        cardIcons.put(ESPSpell.ID, TexLoader.getTexture("awakenedResources/images/ui/ESPSpell.png"));
        //cardIcons.put(Grimoire.ID, TexLoader.getTexture("awakenedResources/images/ui/Grimoire.png"));

        cardColors.put(BurningStudy.ID, Color.RED.cpy());
        cardColors.put(Cryostasis.ID, placeholderColor.cpy());
        cardColors.put(Darkleech.ID, Color.PURPLE.cpy());
        cardColors.put(Thunderbolt.ID, Color.YELLOW.cpy());
        //cardColors.put(AphoticShield.ID, GuardianCharacter.cardRenderColor.cpy());
        cardColors.put(ESPSpell.ID, Color.PINK.cpy());
        //cardColors.put(Grimoire.ID, Color.FIREBRICK.cpy());

        for (int i = 0; i < POWERS_TO_AWAKEN; i++) {
            barSlotAnimTimers.put(i, 0F);
        }
    }

    static {
        spells.add(Thunderbolt.ID);
        spells.add(BurningStudy.ID);
        spells.add(Darkleech.ID);
        spells.add(Cryostasis.ID);
    }

    private static Texture getIconForCard(String cardID) {
        return cardIcons.getOrDefault(cardID, TexLoader.getTexture("awakenedResources/images/ui/defaultSpell.png"));
    }

    private static Color getColorForCard(String cardID) {
        return cardColors.getOrDefault(cardID, defaultNextColor);
    }

    public static void refreshSpells() {
        for (int i = 0; i < spells.size(); i++) {
            String curID = spells.get(i);
            spellCards.removeIf(card -> card.cardID.equals(curID));
        }
        if (AbstractDungeon.player.hasRelic(ZenerDeck.ID)) {
            spellCards.removeIf(card -> card.cardID.equals(ESPSpell.ID));
        }

        for (int i = 0; i < spells.size(); i++) {
            addSpellCard(CardLibrary.getCard(spells.get(i)).makeCopy());
        }

        if (AbstractDungeon.player.hasRelic(ZenerDeck.ID)) {
            addSpellCard(CardLibrary.getCard(ESPSpell.ID).makeCopy());
        }
        setupnext();
    }


    public static void upgradeCaws(int amount) {
        for (AbstractCard c : OrbitingSpells.spellCards) {
            if (c instanceof Caw) {
                c.baseDamage += amount;
                c.applyPowers();
            }
        }
    }

    public static void upgradeall() {
        for (AbstractCard c : spellCards) {
            c.upgrade();
        }
    }

    public static void empty() {
        spellCards.clear();
    }

    public static void addSpellCard(AbstractCard card) {
        if (AbstractDungeon.player.hasPower(MasterRealityPower.POWER_ID)) {
            card.upgrade();
        }
        if (Wiz.isAwakened()) {
            card.upgrade();
        }
        if (spellCards.size() < 10) {
            spellCards.add(card);
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, CardCrawlGame.languagePack.getUIString("awakened:FullSpellbook").TEXT[0], true));
        }
    }

    public static boolean removeSpellCard(AbstractCard card) {
        int idx = getIndexOfCard(card);
        if (idx != -1) {
            spellCards.remove(getIndexOfCard(card));
            att(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    if ((spellCards.isEmpty())) {
                        awaken(5);
                        OrbitingSpells.refreshSpells();
                        ConjureAction.refreshedthisturn = true;
                    } else {
                        atb(new SetUpNextSpellAction());
                    }
                }
            });
            return true;
        }
        return false;
    }

    public static boolean removeSpellCardSpecial(AbstractCard card) {
        int idx = getIndexOfCard(card);
        if (idx != -1) {
            spellCards.remove(getIndexOfCard(card));
            att(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    if ((spellCards.isEmpty())) {
                        awaken(5);
                        OrbitingSpells.refreshSpells();
                        ConjureAction.refreshedthisturn = true;
                    }
                }
            });
            return true;
        }
        return false;
    }


    public static void setupnext() {
        for (AbstractCard c : spellCards) {
            c.tags.remove(UP_NEXT);
        }
        AbstractCard card = Wiz.getRandomItem(spellCards, AbstractDungeon.cardRandomRng);
        int idx = getIndexOfCard(card);
        if (idx != -1) {
            spellCards.get(getIndexOfCard(card)).tags.add(UP_NEXT);
        }
    }


    public static void atBattleStart() {
        refreshSpells();
        barSlotAnimTimers.clear();
        for (int i = 0; i < POWERS_TO_AWAKEN; i++) {
            barSlotAnimTimers.put(i, 0F);
        }
    }

    public static void update() {
        hoveredCard = -1;
        for (int i = 0; i < boxes.size(); i++) {
            boxes.get(i).update();
            if (boxes.get(i).hovered) {
                if (i < spellCards.size()) {
                    hoveredCard = i;
                }
            }
        }
        barBox.update();
        for (int i = 0; i < cappedPowersThisCombat(); i++) {
            barSlotAnimTimers.put(i, barSlotAnimTimers.get(i) + Gdx.graphics.getDeltaTime());
        }
    }

    private static int cappedPowersThisCombat() {
        int powers = powersThisCombat;
        if (powers > POWERS_TO_AWAKEN - 1) {
            powers = POWERS_TO_AWAKEN - 1;
        }
        return powers;
    }

    private static Texture getPipForSlot(int index) {
        if (Wiz.isAwakened()) return pipComplete;
        if (cappedPowersThisCombat() >= index) {
            if (powersThisCombat >= index) {
                if (barSlotAnimTimers.get(index) <= 0.1F) return pipFillAnim1;
                if (barSlotAnimTimers.get(index) <= 0.2F) return pipFillAnim2;
            }
            return filledPip;
        }
        return unfilledPip;
    }

    public static void postPlayerRender(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        drawTextureScaled(sb, panelBG, PANEL_BG_X, PANEL_BG_Y);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, uiStrings.TEXT[0], POSITION_X, Settings.HEIGHT - POSITION_Y + (50 * Settings.scale), Settings.GOLD_COLOR);
        int xr = 0;

        String idNext = "";
        HashMap<String, Integer> spellsFound = new HashMap<>();
        ArrayList<AbstractCard> orderedSpells = new ArrayList<>();
        for (AbstractCard s : spellCards) {
            if (spellsFound.containsKey(s.cardID)) {
                spellsFound.put(s.cardID, spellsFound.get(s.cardID) + 1);
            } else {
                orderedSpells.add(s);
                spellsFound.put(s.cardID, 1);
            }
            if (s.hasTag(UP_NEXT)) idNext = s.cardID;
        }
        for (String id : spellsFound.keySet()) {
            drawTextureScaled(sb, getIconForCard(id), boxes.get(xr).x, boxes.get(xr).y);

            Color textColor = Color.WHITE.cpy();
            if (id.equals(idNext) && DeterministicConjure) {
                textColor = getColorForCard(id);
            }
            String toShow = CardLibrary.getCard(id).name;
            if (Wiz.isAwakened()) toShow = toShow + "+";
            if (spellsFound.get(id) > 1) {
                toShow = toShow + " x" + spellsFound.get(id);
            }
            FontHelper.renderFontLeft(sb, FontHelper.tipHeaderFont, toShow, boxes.get(xr).x + 40F, boxes.get(xr).y + 25F, textColor);
            xr++;
        }

        for (Hitbox h : boxes) {
            h.render(sb);
        }

        for (int i = 0; i < Wiz.POWERS_TO_AWAKEN; i++) {
            drawTextureScaled(sb, (getPipForSlot(i)),
                    barBox.x,
                    barBox.y + (55 * Settings.yScale) * i);
        }
        barBox.render(sb);


        if (hoveredCard != -1) {
            AbstractCard tar = orderedSpells.get(hoveredCard);
            tar.target_x = tar.current_x = (barBox.x + (500 * Settings.scale));
            tar.target_y = tar.current_y = Settings.HEIGHT - ((POSITION_Y + 100 * Settings.scale));
            spellCards.get(hoveredCard).render(sb);
        }
        if (barBox != null) {
            if (barBox.hovered) {
                // showAll = true;
                if ((float) InputHelper.mX < 1400.0F * Settings.scale) {
                    if ((AbstractDungeon.player.chosenClass == AwakenedOneChar.Enums.AWAKENED_ONE)) {
                        TipHelper.renderGenericTip(
                                (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                uiStrings.TEXT[1],
                                uiStrings.TEXT[2]);
                    }

                    if ((AbstractDungeon.player.chosenClass != AwakenedOneChar.Enums.AWAKENED_ONE)) {
                        TipHelper.renderGenericTip(
                                (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                uiStrings.TEXT[1],
                                uiStrings.TEXT[3]);
                    }


                } else {
                    if ((AbstractDungeon.player.chosenClass == AwakenedOneChar.Enums.AWAKENED_ONE)) {
                        TipHelper.renderGenericTip(
                                (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                uiStrings.TEXT[1],
                                uiStrings.TEXT[2]);
                    }

                    if ((AbstractDungeon.player.chosenClass != AwakenedOneChar.Enums.AWAKENED_ONE)) {
                        TipHelper.renderGenericTip(
                                (float) InputHelper.mX + 60.0F * Settings.scale, (float) InputHelper.mY - 50.0F * Settings.scale,
                                uiStrings.TEXT[1],
                                uiStrings.TEXT[3]);
                    }
                }
            }
        }
    }

    public static void drawTextureScaled(SpriteBatch sb, Texture tex, float x, float y) {
        sb.draw(tex, x, y, 0, 0, tex.getWidth() * Settings.scale, tex.getHeight() * Settings.scale, 1, 1, 0, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }

    public static int getIndexOfCard(AbstractCard card) {
        for (int i = 0; i < spellCards.size(); i++) {
            if (spellCards.get(i).cardID.equals(card.cardID)) {
                return i;
            }
        }
        return -1;
    }
}
