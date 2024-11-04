package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import sneckomod.SneckoMod;

public class FlashInThePan extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("FlashInThePan");

    private static final int BLOCK = 9;
    private static final int COST = 2;
    int DhandSize = 0;

    public FlashInThePan() {
        super(ID, COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        exhaust = true;
        SneckoMod.loadJokeCardImage(this, "FlashInThePan.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        DhandSize = 0;
        for (AbstractCard card : p.hand.group) {
            addToBot(new DiscardAction(p, p, 1, false)); // Discard one card at a time
            DhandSize = DhandSize + 1;
        }

        if (DhandSize > 1) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, DhandSize-1), DhandSize-1));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
