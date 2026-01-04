package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class Amass extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Amass");
    private static final int COST = 3;
    private static final int BASE_BLOCK = 9;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public Amass() {
        super(ID, COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        SneckoMod.loadJokeCardImage(this, "Amass.png");
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BASE_BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.applyPowers();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void applyPowers() {
        int realBaseBlock = this.baseBlock;

        int totalEnergyCost = 0;
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            totalEnergyCost += card.costForTurn;
        }

        this.baseBlock += totalEnergyCost * this.magicNumber;
        super.applyPowers();

        this.baseBlock = realBaseBlock;
        this.isBlockModified = this.block != this.baseBlock;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseBlock = this.baseBlock;

        int totalEnergyCost = 0;
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            totalEnergyCost += card.costForTurn;
        }

        this.baseBlock += totalEnergyCost * this.magicNumber;
        super.calculateCardDamage(mo);

        this.baseBlock = realBaseBlock;
        this.isBlockModified = this.block != this.baseBlock;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }
}
