package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import sneckomod.SneckoMod;

public class FlashInThePan extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("FlashInThePan");

    private static final int BLOCK = 13;
    private static final int COST = 2;
    private static final int UPG_BLOCK = 3;

    public FlashInThePan() {
        super(ID, COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        SneckoMod.loadJokeCardImage(this, "FlashInThePan.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();

        int handSize = p.hand.size();
        if (handSize > 0) {
            addToBot(new DiscardAction(p, p, handSize, false));
        }

        if (handSize > 1) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, handSize - 1), handSize - 1));
        }
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}
