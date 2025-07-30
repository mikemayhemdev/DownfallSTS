package awakenedOne.ui;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.actions.SetUpNextSpellAction;
import awakenedOne.cards.*;
import awakenedOne.cards.tokens.spells.*;
import awakenedOne.relics.ZenerDeck;
import awakenedOne.util.TexLoader;
import awakenedOne.util.Wiz;
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
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;

import java.util.ArrayList;
import java.util.HashMap;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;
import static downfall.downfallMod.DeterministicConjure;

public class OrbitingSpells {

    public static final float POSITION_X = 20F * Settings.scale;
    public static final float POSITION_Y = 300F * Settings.scale;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("Spellbook"));

    private static final ArrayList<String> spells = new ArrayList<>();

    public static ArrayList<AbstractCard> spellCards = new ArrayList<>();
    public static ArrayList<Hitbox> boxes = new ArrayList<>();
    private static HashMap<String, Texture> cardIcons = new HashMap<>();
    private static int hoveredCard = -1;
    private static Hitbox barBox = new Hitbox(POSITION_X + 250F * Settings.scale, Settings.HEIGHT - POSITION_Y - 300, 40 * Settings.scale, 350 * Settings.scale);
    private static Texture unfilledPip = TexLoader.getTexture("awakenedResources/images/ui/pip_unfilled.png");
    private static Texture filledPip = TexLoader.getTexture("awakenedResources/images/ui/pip_filled.png");

    static {
        for (int i = 0; i < 10; i++) {
            boxes.add(new Hitbox(POSITION_X, Settings.HEIGHT - (POSITION_Y + (i * (70F * Settings.scale))) - 35, 200F * Settings.scale, 45F * Settings.scale));
        }

        cardIcons.put(BurningStudy.ID, TexLoader.getTexture("awakenedResources/images/ui/BurningStudy.png"));
        cardIcons.put(Cryostasis.ID, TexLoader.getTexture("awakenedResources/images/ui/Cryostasis.png"));
        cardIcons.put(Darkleech.ID, TexLoader.getTexture("awakenedResources/images/ui/Darkleech.png"));
        cardIcons.put(Thunderbolt.ID, TexLoader.getTexture("awakenedResources/images/ui/Thunderbolt.png"));
        cardIcons.put(DeathCoil.ID, TexLoader.getTexture("awakenedResources/images/ui/Deathcoil.png"));
        cardIcons.put(AphoticShield.ID, TexLoader.getTexture("awakenedResources/images/ui/AphoticShield.png"));
        cardIcons.put(ESPSpell.ID, TexLoader.getTexture("awakenedResources/images/ui/ESPSpell.png"));
        cardIcons.put(Grimoire.ID, TexLoader.getTexture("awakenedResources/images/ui/Grimoire.png"));
    }

    private static Texture getIconForCard(AbstractCard tar) {
        return cardIcons.getOrDefault(tar.cardID, TexLoader.getTexture("awakenedResources/images/ui/defaultSpell.png"));
    }

    static {
        spells.add(BurningStudy.ID);
        spells.add(Thunderbolt.ID);
        spells.add(Cryostasis.ID);
        spells.add(Darkleech.ID);
    }



    public static void refreshSpells() {
        spellCards.clear();
        for (int i = 0; i < spells.size(); i++) {
            addSpellCard(CardLibrary.getCard(spells.get(i)).makeCopy());
        }

        int count = (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(q -> q instanceof Deathwish).count();
        for (int i = 0; i < count; i++) {
            addSpellCard(CardLibrary.getCard(DeathCoil.ID).makeCopy());
        }

        int count2 = (int) AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(q -> q instanceof AphoticFount).count();
        for (int i = 0; i < count2; i++) {
            addSpellCard(CardLibrary.getCard(AphoticShield.ID).makeCopy());
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
        spellCards.add(card);
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
    }

    public static void postPlayerRender(SpriteBatch sb) {
        sb.setColor(Color.WHITE.cpy());
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, uiStrings.TEXT[0], POSITION_X, Settings.HEIGHT - POSITION_Y + (50 * Settings.scale), Settings.GOLD_COLOR);
        int xr = 0;

        for (AbstractCard s : spellCards) {
            sb.draw(getIconForCard(s), boxes.get(xr).x, boxes.get(xr).y);
            float dist = FontHelper.getWidth(FontHelper.tipHeaderFont, s.name, 1.0F);

            Color textColor = Color.WHITE.cpy();
            if (s.upgraded || Wiz.isAwakened()) {
                textColor = Color.GREEN.cpy();
            }
            if (s.hasTag(UP_NEXT) && DeterministicConjure) {
                textColor = placeholderColor;
            }
            FontHelper.renderFontLeft(sb, FontHelper.tipHeaderFont, s.name, boxes.get(xr).x + 15F, boxes.get(xr).y + 10F, textColor);
            TipHelper.renderTipEnergy(sb, AbstractCard.orb_red, boxes.get(xr).x + dist + 25.5F, boxes.get(xr).y);
            FontHelper.renderFontLeft(sb, FontHelper.tipHeaderFont, String.valueOf(s.cost), boxes.get(xr).x + dist + 35F, boxes.get(xr).y + 10F, Color.WHITE.cpy());
            xr++;
        }

        for (Hitbox h : boxes) {
            h.render(sb);
        }

        for (int i = 0; i < Wiz.POWERS_TO_AWAKEN; i++) {
            sb.draw((AbstractDungeon.actionManager.cardsPlayedThisCombat.stream().filter(card -> card.type == AbstractCard.CardType.POWER).count() - 1 >= i  || AwakenedOneMod.awakenedthiscombat) ? filledPip : unfilledPip,
                    barBox.x,
                    barBox.y + (40 * Settings.scale) * i);
        }
        barBox.render(sb);


        if (hoveredCard != -1) {
            AbstractCard tar = spellCards.get(hoveredCard);
            tar.target_x = tar.current_x = barBox.x + 200;
            tar.target_y = tar.current_y = Settings.HEIGHT - (POSITION_Y + 100);
            spellCards.get(hoveredCard).render(sb);
        }
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
