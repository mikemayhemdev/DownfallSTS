package gremlin.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.actions.GremlinSwapAction;
import gremlin.orbs.FatGremlin;

import static gremlin.GremlinMod.FAT_GREMLIN;

public class BulkUp extends AbstractGremlinCard {
    public static final String ID = getID("BulkUp");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/bulk_up.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 1;
    private static final int MAGIC = 5;
    private static final int UPGRADE_BONUS = 2;

    public BulkUp()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.tags.add(FAT_GREMLIN);
        setBackgrounds();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(p, p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(new FatGremlin(0)));
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeMagicNumber(UPGRADE_BONUS);
        }
    }
}

