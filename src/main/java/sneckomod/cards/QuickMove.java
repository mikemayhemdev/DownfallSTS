package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class QuickMove extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("QuickMove");

    private static final int BASE_BLOCK = 8;
    private static final int UPG_BASE_BLOCK = 2;

    public QuickMove() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BASE_BLOCK;
        SneckoMod.loadJokeCardImage(this, "QuickMove.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (costForTurn == 0) {
            blck();
            exhaust = true;
        } else {
            for (int i = 0; i < costForTurn; i++) {
                blck();
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BASE_BLOCK);
        }
    }
}
