package sneckomod.cards.unknowns;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import sneckomod.cards.AbstractSneckoCard;

import java.util.ArrayList;
import java.util.function.Predicate;

import static sneckomod.SneckoMod.makeCardPath;


public abstract class AbstractUnknownCard extends AbstractSneckoCard implements StartupCard {
    public AbstractUnknownCard(final String id, final CardType type, final CardRarity rarity) {
        super(id, "SoulRoll", -2, type, rarity, CardTarget.NONE);
    }

    public void upgrade() {
        upgradeName();
    }

    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
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
        p.hand.removeCard(this);
        boolean validCard;

        ArrayList<String> tmp = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            validCard = true;

            if (funkyPredicate.test(c)) {
                if (Loader.isModLoaded("Yohane")) {
                    if (c.cardID.contains("Yohane")) {
                        validCard = false;
                    }
                }
                if (validCard) tmp.add(c.cardID);
            }
        }

        AbstractCard cUnknown;
        if (tmp.size() > 0) {
            cUnknown = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
        } else {
            cUnknown = new com.megacrit.cardcrawl.cards.colorless.Madness();
        }

        if (this.upgraded) cUnknown.upgrade();
        if (cUnknown != null) {
            p.drawPile.removeCard(this);
            AbstractDungeon.player.drawPile.addToRandomSpot(cUnknown);
        }
    }
}
