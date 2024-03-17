package downfall.cards.curses;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import sneckomod.OffclassHelper;


public class Malfunctioning extends CustomCard {
    public static final String ID = downfallMod.makeID("Malfunctioning");
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = downfallMod.assetPath("images/cards/malfunctioning.png");
    private static final CardType TYPE = CardType.CURSE;
    private static final CardRarity RARITY = CardRarity.CURSE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    //Flawed flawed
    public Malfunctioning() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, CardColor.CURSE, RARITY, TARGET);
        this.exhaust = true;
        tags.add(downfallMod.DOWNFALL_CURSE);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        this.flash();
        this.superFlash();
        for (AbstractCard c:AbstractDungeon.player.hand.group){
            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));
            addToBot(new MakeTempCardInDiscardAction(OffclassHelper.getARandomStatus(), 1));
        }
        addToBot(new WaitAction(0.1F));
        addToBot(new WaitAction(0.1F));
        addToBot(new WaitAction(0.1F));
    }

    public AbstractCard makeCopy() {
        return new Malfunctioning();
    }

    public void upgrade() {
    }
}


