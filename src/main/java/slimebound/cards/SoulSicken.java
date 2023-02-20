package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;


public class SoulSicken extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SoulSicken";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/acceleratetoxins.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
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


    public SoulSicken() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);

        //this.baseDamage = 2;
        //this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 3;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        if (m.hasPower(SlimedPower.POWER_ID)) {
            int slimedReduce = m.getPower(SlimedPower.POWER_ID).amount / 2;
            int poisonAmount = this.magicNumber + slimedReduce;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, poisonAmount), poisonAmount, true, AbstractGameAction.AttackEffect.POISON));
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(m, m, SlimedPower.POWER_ID, slimedReduce));

        } else {

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.POISON));

        }


    }

    @Override
    public void triggerOnGlowCheck() {
        slimedGlowCheck();
    }

    public AbstractCard makeCopy() {

        return new SoulSicken();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeMagicNumber(2);

        }

    }
}


