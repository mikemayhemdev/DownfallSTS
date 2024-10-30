package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

import java.util.ArrayList;

import static collector.util.Wiz.atb;

//I used GPT to code this one because it was super complicated, sorry.

public class CrystalBoomerang extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("CrystalBoomerang");
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;

    public CrystalBoomerang() {
        super(ID, COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "CrystalBoomerang.png");
        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> cardsInDiscard = new ArrayList<>(p.discardPile.group);

        if (cardsInDiscard.isEmpty()) {
            return; // Do nothing if the discard pile is empty
        }
        atb(new DiscardPileToTopOfDeckAction(p));
        AbstractCard selectedCard = cardsInDiscard.get(AbstractDungeon.cardRandomRng.random(cardsInDiscard.size() - 1)); //THIS IS RANDOM I WANT A SCREEN BUT IDK HOW HOLOGRAM WORKS

        p.discardPile.removeCard(selectedCard);
        p.hand.addToTop(selectedCard);

        if (!selectedCard.color.equals(p.chosenClass)) {
            addToBot(new GainBlockAction(p, BLOCK));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }
}
