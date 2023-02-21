package hermit.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.UpgradeSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.util.Wiz;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class TakeCover extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(TakeCover.class.getSimpleName());
    public static final String IMG = makeCardPath("take_cover.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final CardStrings defendStrings = CardCrawlGame.languagePack.getCardStrings(Defend_Hermit.ID);
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
        exhaust=true;
        cardsToPreview = new Defend_Hermit();
        loadJokeCardImage(this, "take_cover.png");
    }

    @Override
    public void applyPowers() {
        int num = EnergyPanel.totalCount;

        cardsToPreview.baseBlock = 5;

        if (AbstractDungeon.player.hasRelic("Chemical X")) {
            num += 2;
        }

        if (upgraded)
            num++;

        for(int a=0;a<num;a++)
        {
            cardsToPreview.baseBlock += 3;
            cardsToPreview.upgradedBlock = true;
            cardsToPreview.upgraded = true;

            if (num > 1)
                cardsToPreview.name = defendStrings.NAME + "+" + num;
            else
                cardsToPreview.name = defendStrings.NAME + "+";
        }

        cardsToPreview.initializeDescription();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bazinga = energyOnUse;

        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                int num = EnergyPanel.totalCount;
                if (bazinga != -1) {
                    num = bazinga;
                }

                if (p.hasRelic("Chemical X")) {
                    num += 2;
                    p.getRelic("Chemical X").flash();
                }

                AbstractCard s = (new Defend_Hermit()).makeCopy();

                if (upgraded)
                    num++;

                for(int a=0;a<num;a++)
                {
                    s.baseBlock += 3;
                    s.upgradedBlock = true;
                    s.upgraded = true;

                    if (num > 1)
                        s.name = defendStrings.NAME + "+" + num;
                    else
                        s.name = defendStrings.NAME + "+";
                }


                s.cost = 0;
                s.costForTurn = 0;
                s.isCostModified = true;
                this.addToTop(new MakeTempCardInHandAction(s, 1));

                if (!freeToPlayOnce) {
                    p.energy.use(EnergyPanel.totalCount);
                }

                isDone = true;
            }
        });
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            cardsToPreview.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}