package theHexaghost.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theHexaghost.HexaMod;

public class BustlingGhosts extends AbstractHexaCard{
    public final static String ID = makeID("BustlingGhosts");

    public BustlingGhosts() {
        super(ID, 3, CardType.SKILL, AbstractCard.CardRarity.RARE, CardTarget.SELF);

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard card: p.hand.group) {
            if (card.hasTag(HexaMod.AFTERLIFE)) {
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(card, true, EnergyPanel.getCurrentEnergy(), true, true));
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeBaseCost(2);
        }
    }
}
