package gremlin.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import gremlin.actions.GremlinSwapAction;
import gremlin.actions.MakeEchoAction;
import gremlin.orbs.GremlinWizard;

import static gremlin.GremlinMod.WIZARD_GREMLIN;

public class Tadah extends AbstractGremlinCard {
    public static final String ID = getID("Tadah");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/tadah.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 1;

    public Tadah()
    {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(WIZARD_GREMLIN);
        setBackgrounds();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractCard skill = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL);
        while(skill.cost == -2){
            skill = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL);
        }
        if(upgraded) {
            skill.upgrade();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeEchoAction(skill, 1, 1));
        AbstractDungeon.actionManager.addToBottom(new GremlinSwapAction(new GremlinWizard(0)));
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}


