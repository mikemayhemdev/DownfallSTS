package gremlin.cards;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.actions.EchoACardAction;
import gremlin.GremlinMod;
import gremlin.actions.GremlinSwapAction;
import gremlin.orbs.GremlinWizard;
import sneckomod.SneckoMod;

import static gremlin.GremlinMod.WIZARD_GREMLIN;

public class Tadah extends AbstractGremlinCard {
    public static final String ID = getID("Tadah");
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = strings.NAME;
    private static final String IMG_PATH = "cards/tadah.png";

    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    private static final AbstractCard.CardRarity RARITY = CardRarity.COMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;

    private static final int COST = 0;

    public Tadah() {
        super(ID, NAME, IMG_PATH, COST, strings.DESCRIPTION, TYPE, RARITY, TARGET);
        this.tags.add(WIZARD_GREMLIN);
        setBackgrounds();
        this.cardsToPreview = new Ward();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        GremlinMod.loadJokeCardImage(this, "Tadah.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = new Ward();
        if (upgraded) {
            c.upgrade();
        }
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c, 1));
        addToBot(new GremlinSwapAction(new GremlinWizard(0)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            cardsToPreview.upgrade();
            upgradeName();
            this.rawDescription = strings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}


