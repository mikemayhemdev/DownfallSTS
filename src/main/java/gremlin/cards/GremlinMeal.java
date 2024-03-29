package gremlin.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.GremlinMod;
import com.megacrit.cardcrawl.powers.StrengthPower;
import gremlin.powers.ModifiedLoseStrengthPower;

import static gremlin.GremlinMod.MAD_GREMLIN;

public class GremlinMeal extends AbstractGremlinCard {
    public static final String ID = getID("GremlinMeal");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/gremlin_meal.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 0;
    private static final int MAGIC = 2;
    private static final int UPGRADE_BONUS = 2;

    public GremlinMeal()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);

        this.baseMagicNumber = MAGIC;
        this.magicNumber = baseMagicNumber;
        this.tags.add(MAD_GREMLIN);
        setBackgrounds();
        GremlinMod.loadJokeCardImage(this, "GremlinMeal.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new ModifiedLoseStrengthPower(p, this.magicNumber), this.magicNumber));
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


