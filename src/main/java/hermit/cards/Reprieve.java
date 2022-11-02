package hermit.cards;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;
import hermit.HermitMod;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Reprieve extends AbstractDynamicCard {


    /*
     * SNAPSHOT: Deals 12/16 damage, Dead-On makes it free.
     */


    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Reprieve.class.getSimpleName());
    public static final String IMG = makeCardPath("reprieve.png");

    // /TEXT DECLARATION/

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = downfallMod.Enums.COLOR_YELLOW;


    private static final int COST = 2;


    // /STAT DECLARATION/

    public Reprieve() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = 12;
        loadJokeCardImage(this, "reprieve.png");
        this.tags.add(CardTags.HEALING);
        this.isEthereal = true;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new ReprieveAction(this.magicNumber));
        this.addToBot(new HealAction(p, p, this.magicNumber));
    }

    /*
    public static int countCards(CardGroup varGroup) {
        int count = 0;

        for (AbstractCard c: varGroup.group)
        {
            if (c.color== AbstractCard.CardColor.CURSE)
                count++;
        }

        return count;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            boolean hasCurse = false;

            if (countCards(p.drawPile) > 0 || countCards(p.discardPile) > 0 || countCards(p.hand) > 0){
                hasCurse = true;
            }
            else{
                this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            }

            return hasCurse;
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (countCards(AbstractDungeon.player.drawPile)+countCards(AbstractDungeon.player.discardPile)+countCards(AbstractDungeon.player.hand) > 12) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }
    */

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(3);
            initializeDescription();
        }
    }
}