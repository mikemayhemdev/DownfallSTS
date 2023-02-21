package slimebound.cards;


import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class Replication extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Replication";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/duplicateslimes.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardStrings cardStrings;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public Replication() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), 1, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, CardRarity.UNCOMMON, CardTarget.NONE);
        this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "Replication.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[1], (cards) -> {
            addToTop(new MakeTempCardInDrawPileAction(cards.get(0).makeStatEquivalentCopy(), 1, false, true));
        }));
    }

    public AbstractCard makeCopy() {
        return new Replication();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}

