package hermit.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.HermitMod;
import hermit.actions.FinalCanterAction;
import hermit.actions.ManifestAction;
import hermit.characters.hermit;
import hermit.util.Wiz;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Spite extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Spite.class.getSimpleName());
    public static final String IMG = makeCardPath("spite.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;

    // /STAT DECLARATION/

    public Spite() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage=7;
        loadJokeCardImage(this, "spite.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new FinalCanterAction(m,p,this.damage,this));

        int drawcards = 0;
        for(AbstractCard c : Wiz.p().hand.group)
        {
            if (c.color == CardColor.CURSE)
                drawcards++;
        }
        if (drawcards > 0)
        Wiz.atb(new DrawCardAction(drawcards));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();

        for(AbstractCard c : Wiz.p().hand.group)
        {
            if (c.color == CardColor.CURSE) {
                this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
                return;
            }
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(3);
            initializeDescription();
        }
    }
}