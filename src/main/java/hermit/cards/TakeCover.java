package hermit.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hermit.HermitMod;
import hermit.characters.hermit;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class TakeCover extends AbstractDynamicCard {

    //
    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(TakeCover.class.getSimpleName());
    public static final String IMG = makeCardPath("take_cover.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;



    private static final int COST = -1;


    // /STAT DECLARATION/

    public TakeCover() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust=true;
        this.cardsToPreview = new Defend_Hermit();
        loadJokeCardImage(this, "take_cover.png");

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        int num = energyOnUse;

        if (p.hasRelic("Chemical X")) {
            num += 2;
            p.getRelic("Chemical X").flash();
        }

        AbstractCard s = (new Defend_Hermit()).makeCopy();
        if (this.upgraded)
            s.upgrade();


        s.cost = 0;
        s.costForTurn = 0;
        s.isCostModified = true;
        this.addToTop(new MakeTempCardInHandAction(s, num));

        if (!this.freeToPlayOnce) {
            p.energy.use(EnergyPanel.totalCount);
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}