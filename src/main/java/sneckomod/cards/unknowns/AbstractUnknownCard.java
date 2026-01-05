package sneckomod.cards.unknowns;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import sneckomod.SneckoMod;
import sneckomod.TheSnecko;
import sneckomod.cards.AbstractSneckoCard;
import sneckomod.patches.UnknownExtraUiPatch;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.function.Predicate;


public abstract class AbstractUnknownCard extends AbstractSneckoCard implements StartupCard, CustomSavable<String> {

    public static final float IMPULSE_AMOUNT = 0.15f;
    public static final float MAX_IMPULSE = 0.5f;
    public static final float SCROLL_ROTATION_DELAY = 4.0f;
    public static float rotationDelay;
    public static AbstractCard lastPreviewed;
    public static ArrayList<String> unknownReplacements = new ArrayList<>();
    public static ArrayList<String> unknown0CostReplacements = new ArrayList<>();
    public static ArrayList<String> unknown1CostReplacements = new ArrayList<>();
    public static ArrayList<String> unknown2CostReplacements = new ArrayList<>();
    public static ArrayList<String> unknown3CostReplacements = new ArrayList<>();
    public static ArrayList<String> unknownBlockReplacements = new ArrayList<>();
    public static ArrayList<ArrayList<String>> unknownClassReplacements = new ArrayList<>();
    public static ArrayList<String> unknownColorlessReplacements = new ArrayList<>();
    public static ArrayList<String> unknownCommonAttackReplacements = new ArrayList<>();
    public static ArrayList<String> unknownCommonSkillReplacements = new ArrayList<>();
    public static ArrayList<String> unknownDexterityReplacements = new ArrayList<>();
    public static ArrayList<String> unknownExhaustReplacements = new ArrayList<>();
    public static ArrayList<String> unknownRareAttackReplacements = new ArrayList<>();
    public static ArrayList<String> unknownRarePowerReplacements = new ArrayList<>();
    public static ArrayList<String> unknownRareSkillReplacements = new ArrayList<>();
    public static ArrayList<String> unknownStrengthReplacements = new ArrayList<>();
    public static ArrayList<String> unknownStrikeReplacements = new ArrayList<>();
    public static ArrayList<String> unknownUncommonAttackReplacements = new ArrayList<>();
    public static ArrayList<String> unknownUncommonPowerReplacements = new ArrayList<>();
    public static ArrayList<String> unknownUncommonSkillReplacements = new ArrayList<>();
    public static ArrayList<String> unknownVulnerableReplacements = new ArrayList<>();
    public static ArrayList<String> unknownWeakReplacements = new ArrayList<>();
    public static ArrayList<String> unknownXCostReplacements = new ArrayList<>();
    public static ArrayList<String> unknownDrawReplacements = new ArrayList<>();
    public static ArrayList<String> unknownBossReplacements = new ArrayList<>();
    public AbstractCard lastUnknownRoll;
    public float rotationTimer;
    public float scrollImpulse;
    public AbstractUnknownCard(final String id, final CardType type, final CardRarity rarity) {
        super(id, -2, type, rarity, CardTarget.NONE);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
        cardsToPreview = CardLibrary.cards.get("Madness");
    }
    public AbstractUnknownCard(final String id, final CardType type, final CardRarity rarity, CardColor color) {
        super(id, -2, type, rarity, CardTarget.NONE, color);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
        cardsToPreview = CardLibrary.cards.get("Madness");
    }
    public AbstractUnknownCard(final String id, final String img, final CardType type, final CardRarity rarity, boolean IsClass) {
        super(id, img, -2, type, rarity, CardTarget.NONE, IsClass);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
        cardsToPreview = CardLibrary.cards.get("Madness");
    }

    public AbstractUnknownCard(final String id, final String img, final CardType type, final CardRarity rarity) {
        super(id, img, -2, type, rarity, CardTarget.NONE);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
        cardsToPreview = CardLibrary.cards.get("Madness");
    }

    public AbstractUnknownCard(final String id, final String img, final CardType type, final CardRarity rarity, CardColor color) {
        super(id, img, -2, type, rarity, CardTarget.NONE, color);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
        cardsToPreview = CardLibrary.cards.get("Madness");
    }

    public static void updateReplacements(ArrayList<Predicate<AbstractCard>> funkyPredicates, ArrayList<ArrayList<String>> funkyLists) {
        boolean validCard;

        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (!c.isSeen)
                UnlockTracker.markCardAsSeen(c.cardID);
            validCard = !c.hasTag(CardTags.STARTER_STRIKE) && !c.hasTag(CardTags.STARTER_DEFEND) && c.type != CardType.STATUS && c.color != CardColor.CURSE && c.type != CardType.CURSE && c.rarity != CardRarity.SPECIAL && !c.hasTag(SneckoMod.BANNEDFORSNECKO);

            if (AbstractDungeon.player != null && validCard) {
                validCard = c.color != AbstractDungeon.player.getCardColor();
            } else if (AbstractDungeon.player == null && validCard) {
                validCard = c.color != TheSnecko.Enums.SNECKO_CYAN;
            }

            for (int i = 0; i < funkyPredicates.size(); i++) {
                Predicate<AbstractCard> funkyPredicate = funkyPredicates.get(i);
                if (funkyPredicate.test(c) && (SneckoMod.isPureSneckoModeEnabled() || (SneckoMod.validColors.contains(c.color) || (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass != TheSnecko.Enums.THE_SNECKO)) || i >= 22)) {
                    if (validCard) {
                        ArrayList<String> s = funkyLists.get(funkyPredicates.indexOf(funkyPredicate));
                        if (s == null) {
                            s = new ArrayList<>();
                        }
                        s.add(c.cardID);
                    }
                }
            }
        }

        // Sort the card lists so the preview shows them in order
        for (ArrayList<String> cardList : funkyLists) {
            cardList.sort((lhs, rhs) -> {
                AbstractCard rCard = CardLibrary.getCard(lhs);
                AbstractCard lCard = CardLibrary.getCard(rhs);
                return (lCard.color.name() + lCard.rarity.ordinal() + lCard.name).compareTo(rCard.color.name() + rCard.rarity.ordinal() + rCard.name);
            });
        }
    }

    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerAnything;
    }

    // public static ArrayList<String> unknownEtherealReplacements = new ArrayList<>();
//
    @Override
    public void update() {
        super.update();
        updateInput(false);
    }

    public void updateInput(boolean isSingleView) {
        if (this.hb.hovered || isSingleView) {
            if (InputHelper.scrolledDown) {
                rotationDelay = SCROLL_ROTATION_DELAY;
                scrollImpulse -= IMPULSE_AMOUNT;
                if (scrollImpulse < -MAX_IMPULSE) scrollImpulse = -MAX_IMPULSE;
            } else if (InputHelper.scrolledUp) {
                rotationDelay = SCROLL_ROTATION_DELAY;
                scrollImpulse += IMPULSE_AMOUNT;
                if (scrollImpulse > MAX_IMPULSE) scrollImpulse = MAX_IMPULSE;
            }
        }
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if ((AbstractDungeon.player != null) && (AbstractDungeon.player.isDraggingCard)) {
            return;
        }
        if (lastPreviewed != this) {
            // reset delay between scrolling and resuming auto scroll if a different card is previewed
            lastPreviewed = this;
            rotationDelay = 0.0f;
        }
        renderCardPreviewImpl(sb, false);
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        renderCardPreviewImpl(sb, true);
    }

    public void renderCardPreviewImpl(SpriteBatch sb, boolean isSingleView) {
        ArrayList<String> cardList = myList();
        if (cardList.size() == 0) {
            // render default preview card: Madness
            if (isSingleView) {
                super.renderCardPreviewInSingleView(sb);
            } else {
                super.renderCardPreview(sb);
            }

            return;
        }

        float interval = 1.5f;

        if (rotationTimer <= 0F) {
            rotationTimer = interval * cardList.size();
        }
        int cardIdx = (int) (rotationTimer / interval) % cardList.size();
        AbstractCard[] cards = new AbstractCard[7];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = CardLibrary.cards.get(cardList.get(cardIdx)).makeCopy(); // please cache these
            UnknownExtraUiPatch.parentCard.set(cards[i], this);
            if (upgraded) {
                cards[i].upgrade();
            }

            cardIdx = (cardIdx + 1) % cardList.size();
        }

        float phase = (rotationTimer % interval) / interval;

        float tmpScale = this.drawScale * 0.8F;


        for (int i = 0; i < cards.length; i++) {
            AbstractCard card = cards[i];

            if (isSingleView) {
                card.drawScale = 0.8f;
                card.current_x = (485f * Settings.scale);
                card.current_y = (this.current_y + (IMG_HEIGHT * (1.1f * (-phase + i - 2))) * card.drawScale);
            } else {
                card.current_y = (this.current_y + (IMG_HEIGHT * (0.9f * (-phase + i - 2))) * this.drawScale);
                if (this.current_x > Settings.WIDTH * 0.75F) {
                    card.current_x = (this.current_x + (IMG_WIDTH * 0.9f + 16.0F) * this.drawScale);
                } else {
                    card.current_x = (this.current_x - (IMG_WIDTH * 0.9f + 16.0F) * this.drawScale);
                }
                card.drawScale = tmpScale;
            }


            card.render(sb);
        }
        if (rotationDelay > 0.0f) {
            rotationDelay -= Gdx.graphics.getDeltaTime();
        } else {
            rotationTimer -= Gdx.graphics.getDeltaTime();
        }
        rotationTimer += scrollImpulse;
        scrollImpulse *= 0.85;
        if (Math.abs(scrollImpulse) < 0.01) scrollImpulse = 0;

    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        // Single-card preview makes a copy of the card every frame, so copy the rotation timer to that card
        AbstractUnknownCard result = (AbstractUnknownCard) super.makeStatEquivalentCopy();
        result.rotationTimer = rotationTimer;
        result.scrollImpulse = scrollImpulse;
        return result;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            if (UPGRADE_DESCRIPTION != null) {
                rawDescription = UPGRADE_DESCRIPTION;
            }
            initializeDescription();
        }
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                replaceUnknownFromHand();
                isDone = true;
            }
        });
    }

    public abstract Predicate<AbstractCard> myNeeds();

    public abstract ArrayList<String> myList();

    @Override
    public boolean atBattleStartPreDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                replaceUnknown();
                isDone = true;
            }
        });
        return false;
    }


    public void replaceUnknown() {
        AbstractPlayer p = AbstractDungeon.player;
        int idx = p.drawPile.group.indexOf(this);

        AbstractCard cUnknown;
        if (myList().isEmpty()) {
            cUnknown = new Madness();
        } else {
            cUnknown = CardLibrary.cards.get(myList().get(AbstractDungeon.cardRng.random(0, myList().size() - 1))).makeStatEquivalentCopy();
        }

        if (this.upgraded) cUnknown.upgrade();
        if (StSLib.getMasterDeckEquivalent(this) != null) {
            ((AbstractUnknownCard) StSLib.getMasterDeckEquivalent(this)).lastUnknownRoll = cUnknown.makeCopy();

        }

        p.hand.removeCard(this);
        p.drawPile.removeCard(this);
        UnknownExtraUiPatch.parentCard.set(cUnknown, this);
        if (cUnknown.isInnate) {
            AbstractDungeon.player.drawPile.addToTop(cUnknown);
        } else {
            //This check should always pass, but a crash report indicates it rarely can not.
            //So if the idx is somehow outside the array's bounds, add the card to bottom instead.
            if (idx > -1 && idx < p.drawPile.group.size()) {
                AbstractDungeon.player.drawPile.group.add(idx, cUnknown);
            } else {
                AbstractDungeon.player.drawPile.addToBottom(cUnknown);
            }
        }
    }

    public AbstractCard generateFromPoolButNotIntoHand() { // used for some relics, so when generating cards, pull cards from this's unknwon pool instead of generating a meaningless unknowncard
        AbstractCard cUnknown;

        if (myList().size() > 0) {
            cUnknown = CardLibrary.cards.get(myList().get(AbstractDungeon.cardRng.random(0, myList().size() - 1))).makeStatEquivalentCopy();
        } else {
            cUnknown = new Madness();
        }

        if (this.upgraded) cUnknown.upgrade();

        UnknownExtraUiPatch.parentCard.set(cUnknown, this);

        return cUnknown;
    }

    public void replaceUnknownFromHand() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractCard cUnknown;
        if (myList().size() > 0) {
            cUnknown = CardLibrary.cards.get(myList().get(AbstractDungeon.cardRng.random(0, myList().size() - 1))).makeStatEquivalentCopy();
        } else {
            cUnknown = new Madness();
        }

        if (this.upgraded) cUnknown.upgrade();

        p.limbo.removeCard(this);
        p.hand.removeCard(this);
        p.drawPile.removeCard(this);
        UnknownExtraUiPatch.parentCard.set(cUnknown, this);

        if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.drawPile.moveToDiscardPile(cUnknown);
            AbstractDungeon.player.createHandIsFullDialog();
        } else {
            cUnknown.current_x = this.current_x;
            cUnknown.current_y = this.current_y;
            this.drawScale = cUnknown.drawScale;
            AbstractDungeon.player.hand.addToTop(cUnknown);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
        }
    }

    @Override
    public String onSave() {
        if (lastUnknownRoll != null) {
            return lastUnknownRoll.cardID;
        }
        return "";
    }

    @Override
    public void onLoad(String cardID) {
        // Not the most elegant solution but I can't make AbstractDamageModifier serializable because it isn't my code.
        if (cardID != "") {
            if (CardLibrary.isACard(cardID)) {
                lastUnknownRoll = CardLibrary.getCard(cardID).makeCopy();
            }

        }
    }

    @Override
    public Type savedType() {
        return String.class;
    }
}