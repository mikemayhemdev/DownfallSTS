package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.RandomLickCardAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;


public class ItLooksTasty extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:ItLooksTasty";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/itlookstasty.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public ItLooksTasty() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        this.baseDamage = 8;
        this.cardsToPreview = new Lick();

    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        if (m.hasPower(SlimedPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RandomLickCardAction(this.upgraded));
            /*
            if (upgraded) {
                AbstractDungeon.actionManager.addToBottom(new RandomLickCardAction(false));
            }
            */
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));


    }

    @Override
    public void triggerOnGlowCheck() {
        slimedGlowCheck();
    }


    public AbstractCard makeCopy() {

        return new ItLooksTasty();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeDamage(2);
            cardsToPreview.upgrade();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }

    }
}


