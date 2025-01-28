package hermit.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.actions.FinalCanterAction;
import hermit.characters.hermit;
import hermit.util.Wiz;
import sneckomod.SneckoMod;


import java.util.Iterator;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class FinalCanter extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(FinalCanter.class.getSimpleName());
    public static final String IMG = makeCardPath("final_canter.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 0;

    // /STAT DECLARATION/

    public FinalCanter() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage=10;
        magicNumber = baseMagicNumber = 0;
        this.selfRetain = true;
        exhaust=true;
        loadJokeCardImage(this, "final_canter.png");
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        magicNumber = countCards();

        for (int ii=0; ii < magicNumber; ii++)
            Wiz.atb(new FinalCanterAction(m,p,this.damage,this));

        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void applyPowers() {
        baseMagicNumber = magicNumber = countCards();
        super.applyPowers();
        if (magicNumber == 1)
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.UPGRADE_DESCRIPTION;
        else
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public static int countCards() {
        int count = 0;

        for (AbstractCard c: AbstractDungeon.player.hand.group)
        {
            if (c.color==CardColor.CURSE)
                count++;
        }

        return count;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}