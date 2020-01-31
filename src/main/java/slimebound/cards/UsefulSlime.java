package slimebound.cards;


import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;


public class UsefulSlime extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:UsefulSlime";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/usefulslime.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }


    public UsefulSlime() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);


        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 1;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 2));

    }

    public void triggerWhenDrawn() {
        if ((AbstractDungeon.player.hasPower("Evolve")) && (!AbstractDungeon.player.hasPower("No Draw"))) {
            AbstractDungeon.player.getPower("Evolve").flash();
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DrawCardAction(AbstractDungeon.player,


                    AbstractDungeon.player.getPower("Evolve").amount));
        }
    }

    public AbstractCard makeCopy() {
        return new UsefulSlime();
    }

    public void upgrade() {
    }
}

