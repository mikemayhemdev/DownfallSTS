package sneckomod.cards.unknowns;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import sneckomod.SneckoMod;
import sneckomod.cards.AbstractSneckoCard;

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
    public static ArrayList<String> unknownEtherealReplacements = new ArrayList<>();
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

    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F) {
                rotationTimer = 0.25F;
                if (myList().size() == 0) {
                    cardsToPreview = CardLibrary.cards.get("Madness");
                } else {
                    cardsToPreview = CardLibrary.cards.get(myList().get(previewIndex));
                }
                if (previewIndex == myList().size() - 1) {
                    previewIndex = 0;
                } else {
                    previewIndex++;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void unhover() {
        super.unhover();
        previewIndex = 0;
    }

    public void upgrade() {
        upgradeName();
        String[] funky = rawDescription.split(unknownUpgrade[0]);
        funky[1] = unknownUpgrade[1] + funky[1];
        rawDescription = String.join(unknownUpgrade[0], funky);
        initializeDescription();
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
            validCard = !c.hasTag(CardTags.STARTER_STRIKE) && !c.hasTag(CardTags.STARTER_DEFEND) && c.type != CardType.STATUS && c.color != CardColor.CURSE && c.type != CardType.CURSE && c.rarity != CardRarity.SPECIAL && c.color != AbstractDungeon.player.getCardColor() && !c.hasTag(SneckoMod.BANNEDFORSNECKO);

            for (Predicate<AbstractCard> funkyPredicate : funkyPredicates) {
                if (funkyPredicate.test(q) && (SneckoMod.pureSneckoMode || SneckoMod.validColors.contains(q.color))) {
                    if (validCard) {
                        System.out.println("WE ARE HERE!");
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

        AbstractCard cUnknown;
        cUnknown = CardLibrary.cards.get(myList().get(AbstractDungeon.cardRng.random(0, myList().size() - 1))).makeStatEquivalentCopy();

        if (this.upgraded) cUnknown.upgrade();
        if (cUnknown != null) {
            p.hand.removeCard(this);
            p.drawPile.removeCard(this);
            if (cUnknown.cardsToPreview == null)
                cUnknown.cardsToPreview = this.makeStatEquivalentCopy();
            AbstractDungeon.player.drawPile.addToRandomSpot(cUnknown);
        }
    }

    public void replaceUnknownFromHand() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractCard cUnknown;
        cUnknown = CardLibrary.cards.get(myList().get(AbstractDungeon.cardRng.random(0, myList().size() - 1))).makeStatEquivalentCopy();

        if (this.upgraded) cUnknown.upgrade();
        if (cUnknown != null) {
            p.limbo.removeCard(this);
            p.hand.removeCard(this);
            p.drawPile.removeCard(this);
            if (cUnknown.cardsToPreview == null)
                cUnknown.cardsToPreview = this.makeStatEquivalentCopy();
            AbstractDungeon.player.hand.addToTop(cUnknown);
        }
    }
}
