package gremlin.cards.pseudocards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.actions.GremlinSwapAction;
import gremlin.cards.AbstractGremlinCard;
import gremlin.orbs.SneakyGremlin;

import static gremlin.GremlinMod.SNEAKY_GREMLIN;

public class SneakyGremlinCard extends AbstractGremlinCard {
    private static final String ID = getID("SneakyGremlin");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/SneakyGremlin.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 0;

    public SneakyGremlinCard()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(SNEAKY_GREMLIN);
        setBackgrounds();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(new SneakyGremlin(0)));
        this.dontTriggerOnUseCard = true;
    }

    public AbstractCard makeCopy()
    {
        return new SneakyGremlinCard();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
        }
    }
}


