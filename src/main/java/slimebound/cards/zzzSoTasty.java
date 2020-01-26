package slimebound.cards;



import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import slimebound.SlimeboundMod;
import slimebound.actions.MultiLickAction;
import slimebound.patches.AbstractCardEnum;


public class zzzSoTasty extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:zzzSoTasty";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/rollthrough.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = -1;


    public zzzSoTasty() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 7;


        this.isMultiDamage = true;
        this.exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }

        AbstractDungeon.actionManager.addToBottom(new MultiLickAction(p,

                AbstractDungeon.getMonsters().getRandomMonster(true), new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), this.energyOnUse));

    }


    public AbstractCard makeCopy() {

        return new zzzSoTasty();

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


