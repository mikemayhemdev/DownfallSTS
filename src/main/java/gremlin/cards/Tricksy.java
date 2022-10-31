package gremlin.cards;

import com.megacrit.cardcrawl.actions.defect.ScrapeAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.actions.GremlinSwapAction;
import gremlin.actions.TricksyFollowUpAction;
import gremlin.orbs.SneakyGremlin;

import static gremlin.GremlinMod.SNEAKY_GREMLIN;

public class Tricksy extends AbstractGremlinCard {
    public static final String ID = getID("Tricksy");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/tricksy.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 1;
    private static final int MAGIC = 4;
    private static final int UPGRADE_BONUS = 2;

    public Tricksy()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.tags.add(SNEAKY_GREMLIN);
        setBackgrounds();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ScrapeAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.4f));
        AbstractDungeon.actionManager.addToBottom(new TricksyFollowUpAction());
        AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(new SneakyGremlin(0)));
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


