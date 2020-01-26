package slimebound.cards;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class zzzStrikeFrailPoint extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:zzzStrikeFrailPoint";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/strikefrail.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;


    public zzzStrikeFrailPoint() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 9;
        this.tags.add(AbstractCard.CardTags.STRIKE);


    }


    public void use(AbstractPlayer p, AbstractMonster m) {


        if (m.hasPower("Frail")) {
            AbstractDungeon.actionManager.addToBottom(new RemoveAllBlockAction(m, p));


            if (m.getPower("Frail").amount > 1) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new FrailPower(p, -1, false), -1, true, AbstractGameAction.AttackEffect.NONE));
            } else {
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(m, p, "Frail"));

            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        }

    }


    public AbstractCard makeCopy() {

        return new zzzStrikeFrailPoint();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeDamage(3);

        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


