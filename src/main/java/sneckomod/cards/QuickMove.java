package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;
import sneckomod.actions.MuddleRandomCardAction;

public class QuickMove extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("QuickMove");

    private static final int BASE_BLOCK = 7;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 1;
    //private static final int UPG_MAGIC = 1;

    public QuickMove() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BASE_BLOCK;
        tags.add(SneckoMod.OVERFLOW);
        SneckoMod.loadJokeCardImage(this, "QuickMove.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (isOverflowActive(this)) {
            addToBot(new SelectCardsInHandAction(magicNumber, "Muddle",
                    (AbstractCard c) -> true,
                    (cards) -> {
                        for (AbstractCard card : cards) {
                            addToBot(new MuddleAction(card));
                        }
                    }
            ));
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
