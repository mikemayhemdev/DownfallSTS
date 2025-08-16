package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import sneckomod.SneckoMod;

@Deprecated
@CardIgnore

public class ImmovableObject extends AbstractSneckoCard {

    public static final String ID = makeID("ImmovableObject");

    private static final int COST = 2;
    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 12;
    private static final int UPG_MAGIC = -2;

    public ImmovableObject() {
        super(ID, COST, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "ImmovableObject.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));

        int totalCost = 0;
        for (AbstractCard card : p.hand.group) {
            totalCost += card.costForTurn;
        }

        if (totalCost > magicNumber) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    p.addBlock(block);
                    isDone = true;
                }
            });
            addToBot(new ExhaustSpecificCardAction(this, p.hand));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}
