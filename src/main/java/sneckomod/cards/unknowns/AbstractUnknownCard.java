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
import sneckomod.SneckoMod;
import sneckomod.cards.AbstractSneckoCard;

import java.util.ArrayList;


public abstract class AbstractUnknownCard extends AbstractSneckoCard implements StartupCard {
    public AbstractUnknownCard(final String id, final CardType type, final CardRarity rarity) {
        super(id, -2, type, rarity, CardTarget.NONE);
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
                replaceUnknown(false);
                isDone = true;
            }
        });
        return false;
    }

    public void replaceUnknown(boolean toHand) {
        AbstractPlayer p = AbstractDungeon.player;
        p.hand.removeCard(this);
        boolean validCard;

        ArrayList<String> tmp = new ArrayList<>();
        for (AbstractCard c : CardLibrary.getAllCards()) {
            validCard = true;

            if ((c.rarity == this.rarity) && c.type == this.type && !(c instanceof AbstractUnknownCard)) {
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
            if ((cUnknown.cost >= 0) && (!cUnknown.hasTag(SneckoMod.SNEKPROOF)) && cUnknown.rarity != CardRarity.BASIC && cUnknown.rarity != CardRarity.SPECIAL) {
                int newCost = AbstractDungeon.cardRandomRng.random(3);
                if (cUnknown.cost != newCost) {
                    cUnknown.cost = newCost;
                    cUnknown.costForTurn = cUnknown.cost;
                    cUnknown.isCostModified = true;
                }
            }

            UnlockTracker.markCardAsSeen(cUnknown.cardID);
            if (!toHand) {
                p.drawPile.removeCard(this);
                AbstractDungeon.player.drawPile.addToRandomSpot(cUnknown);
            } else {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cUnknown, 1, true));
            }
        }
    }
}
