package sneckomod.cards.unknowns;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import sneckomod.cards.AbstractSneckoCard;

import java.util.ArrayList;
import java.util.function.Predicate;


public abstract class AbstractUnknownCard extends AbstractSneckoCard implements StartupCard {
    private String[] unknownUpgrade = CardCrawlGame.languagePack.getUIString(makeID("Unknown")).TEXT;

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
                replaceUnknownFromHand(myNeeds());
                isDone = true;
            }
        });
    }

    @Override
    public boolean atBattleStartPreDraw() {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                replaceUnknown(myNeeds());
                isDone = true;
            }
        });
        return false;
    }

    public abstract Predicate<AbstractCard> myNeeds();

    public void replaceUnknown(Predicate<AbstractCard> funkyPredicate) {
        AbstractPlayer p = AbstractDungeon.player;
        boolean validCard;

        ArrayList<String> tmp = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (!c.isSeen)
                UnlockTracker.markCardAsSeen(c.cardID);
            AbstractCard q = c.makeCopy();
            validCard = !c.hasTag(CardTags.STARTER_STRIKE) && !c.hasTag(CardTags.STARTER_DEFEND) && c.type != CardType.STATUS && c.color != CardColor.CURSE && c.type != CardType.CURSE && c.rarity != CardRarity.SPECIAL && c.color != AbstractDungeon.player.getCardColor();
            if (this.upgraded) {
                if (!c.canUpgrade()) validCard = false;
                if (validCard) q.upgrade();
            }
            if (funkyPredicate.test(q)) {
                if (validCard) tmp.add(c.cardID);
            }
        }

        AbstractCard cUnknown;
        if (tmp.size() > 0) {
            cUnknown = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1))).makeStatEquivalentCopy();
        } else {
            cUnknown = new com.megacrit.cardcrawl.cards.colorless.Madness();
        }

        if (this.upgraded) cUnknown.upgrade();
        if (cUnknown != null) {
            p.hand.removeCard(this);
            p.drawPile.removeCard(this);
            if (cUnknown.cardsToPreview == null) cUnknown.cardsToPreview = this.makeStatEquivalentCopy();
            AbstractDungeon.player.drawPile.addToRandomSpot(cUnknown);
        }
    }

    public void replaceUnknownFromHand(Predicate<AbstractCard> funkyPredicate) {
        AbstractPlayer p = AbstractDungeon.player;
        boolean validCard;

        ArrayList<String> tmp = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            if (!c.isSeen)
                UnlockTracker.markCardAsSeen(c.cardID);
            AbstractCard q = c.makeCopy();
            validCard = !c.hasTag(CardTags.STARTER_STRIKE) && !c.hasTag(CardTags.STARTER_DEFEND) && c.type != CardType.STATUS && c.color != CardColor.CURSE && c.type != CardType.CURSE && c.rarity != CardRarity.SPECIAL && c.color != AbstractDungeon.player.getCardColor();
            if (this.upgraded) {
                if (!c.canUpgrade()) validCard = false;
                if (validCard) q.upgrade();
            }
            if (funkyPredicate.test(q)) {
                if (validCard) tmp.add(c.cardID);
            }
        }

        AbstractCard cUnknown;
        if (tmp.size() > 0) {
            cUnknown = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1))).makeStatEquivalentCopy();
        } else {
            cUnknown = new com.megacrit.cardcrawl.cards.colorless.Madness();
        }

        if (this.upgraded) cUnknown.upgrade();
        if (cUnknown != null) {
            p.limbo.removeCard(this);
            p.hand.removeCard(this);
            p.drawPile.removeCard(this);
            if (cUnknown.cardsToPreview == null) cUnknown.cardsToPreview = this.makeStatEquivalentCopy();
            AbstractDungeon.player.hand.addToTop(cUnknown);
        }
    }
}
