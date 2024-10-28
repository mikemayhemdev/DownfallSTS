package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import hermit.cards.AbstractDynamicCard;
import sneckomod.SneckoMod;

public class MintCondition extends AbstractSneckoCard {

    public final static String ID = makeID("MintCondition");

    private static final int BASE_BLOCK = 5;
    private static final int MAGIC = 2;
    private static final int UPGRADE_BLOCK = 2;

    //this is a show-off clone

    public MintCondition() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BASE_BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.tags.add(SneckoMod.OVERFLOW);
        SneckoMod.loadJokeCardImage(this, "MintCondition.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int suitCount = findSuitinHand();

        for (int i = 0; i < suitCount; i++) {
            addToBot(new GainBlockAction(p, p, block));
        }

        if (isOverflowActive(this)) {
            addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber), magicNumber));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            initializeDescription();
        }
    }
}
