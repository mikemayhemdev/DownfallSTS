package sneckomod.cards.unknowns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import sneckomod.SneckoMod;
import sneckomod.TheSnecko;
import sneckomod.cards.AbstractSneckoCard;
import sneckomod.patches.UnknownExtraUiPatch;

import java.util.ArrayList;
import java.util.function.Predicate;


public abstract class AbstractUnknownCard extends AbstractSneckoCard implements StartupCard {
    public AbstractUnknownCard(final String id, final CardType type, final CardRarity rarity) {
        super(id, -2, type, rarity, CardTarget.NONE);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
    }

    public AbstractUnknownCard(final String id, final CardType type, final CardRarity rarity, CardColor color) {
        super(id, -2, type, rarity, CardTarget.NONE, color);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
    }

    public AbstractUnknownCard(final String id, final String img, final CardType type, final CardRarity rarity) {
        super(id, img, -2, type, rarity, CardTarget.NONE);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
    }

    public AbstractUnknownCard(final String id, final String img, final CardType type, final CardRarity rarity, CardColor color) {
        super(id, img, -2, type, rarity, CardTarget.NONE, color);
        tags.add(CardTags.HEALING);
        purgeOnUse = true;
    }

    public TextureAtlas.AtlasRegion getOverBannerTex() {
        return SneckoMod.overBannerAnything;
    }

    private float rotationTimer;

    private int previewIndex;


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
    // public static ArrayList<String> unknownEtherealReplacements = new ArrayList<>();

    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 0.5F;
                if (myList().size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = CardLibrary.cards.get(myList().get(previewIndex)).makeCopy();
                }
                if (previewIndex == myList().size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
                if (upgraded) {
                    cardsToPreview.upgrade();
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            String[] funky = rawDescription.split(unknownUpgrade[0]);
            funky[1] = unknownUpgrade[1] + funky[1];
            rawDescription = String.join(unknownUpgrade[0], funky);
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

    public static void updateReplacements(ArrayList<Predicate<AbstractCard>> funkyPredicates, ArrayList<ArrayList<String>> funkyLists) {
        boolean validCard;

        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (!c.isSeen)
                UnlockTracker.markCardAsSeen(c.cardID);
            AbstractCard q = c.makeCopy();
            validCard = !c.hasTag(CardTags.STARTER_STRIKE) && !c.hasTag(CardTags.STARTER_DEFEND) && c.type != CardType.STATUS && c.color != CardColor.CURSE && c.type != CardType.CURSE && c.rarity != CardRarity.SPECIAL && !c.hasTag(SneckoMod.BANNEDFORSNECKO);

            if (AbstractDungeon.player != null && validCard) {
                validCard = c.color != AbstractDungeon.player.getCardColor();
            } else if (AbstractDungeon.player == null && validCard) {
                validCard = c.color != TheSnecko.Enums.SNECKO_CYAN;
            }

            for (int i = 0; i < funkyPredicates.size(); i++) {
                Predicate<AbstractCard> funkyPredicate = funkyPredicates.get(i);
                if (funkyPredicate.test(q) && (SneckoMod.pureSneckoMode || (SneckoMod.validColors.contains(q.color) || (AbstractDungeon.player != null && AbstractDungeon.player.chosenClass != TheSnecko.Enums.THE_SNECKO)) || i >= 22)) {
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

    }

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

        p.hand.removeCard(this);
        p.drawPile.removeCard(this);
        UnknownExtraUiPatch.parentCard.set(cUnknown, this);
        if (cUnknown.isInnate) {
            AbstractDungeon.player.drawPile.addToTop(cUnknown);
        }
        else {
            AbstractDungeon.player.drawPile.group.add(idx, cUnknown);
        }
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
        AbstractDungeon.player.hand.addToTop(cUnknown);
    }

}
