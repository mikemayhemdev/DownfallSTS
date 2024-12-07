package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class LilGuardian extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("LilGuardian");
    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADE_BLOCK = 2;

    public LilGuardian() {
        super(ID, COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        SneckoMod.loadJokeCardImage(this, "LilGuardian.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard card) {
        if (card.costForTurn >= 2 && AbstractDungeon.player.hand.contains(this)) {
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
            addToBot(new DiscardSpecificCardAction(this, AbstractDungeon.player.hand));
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
