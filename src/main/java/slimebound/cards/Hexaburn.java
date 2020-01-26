package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import slimebound.SlimeboundMod;
import slimebound.actions.DividerAction;


public class Hexaburn extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Hexaburn";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/divider.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 0;


    public Hexaburn() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);


        this.baseDamage = 0;
        this.magicNumber = this.baseMagicNumber = 6;
        this.exhaust = true;



    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int bonus = 0;
        if (upgraded){
         bonus = player.currentHealth / 6;
        } else {
            bonus = player.currentHealth / 10;
        }
        return tmp + bonus;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ScreenOnFireEffect(), .2F));

        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));


    }


    public AbstractCard makeCopy() {

        return new Hexaburn();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            //upgradeDamage(1);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();


        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


