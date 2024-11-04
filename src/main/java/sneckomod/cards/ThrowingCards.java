package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import sneckomod.SneckoMod;
import automaton.actions.EasyXCostAction;

public class ThrowingCards extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("ThrowingCards");
    private static final int BLOCK = 4;
    private static final int UPGRADE_BLOCK = 2;

    public ThrowingCards() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                blck();
            }
            if (effect > 0) {
                applyToSelf(new DrawCardNextTurnPower(p, effect));
            }
            return true;
        }));
    }

    @Override
    public void upgrade() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}
