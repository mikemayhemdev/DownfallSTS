package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.EnergyToCidAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.slimes.SlimeHelper;
import sneckomod.SneckoMod;


public class DivideAndConquer extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DivideAndConquer";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "cards/conquer.png";
    private static final CardStrings cardStrings;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }


    public DivideAndConquer() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.exhaust = true;
        this.isMultiDamage = true;
        baseDamage = 10;
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
        SlimeboundMod.loadJokeCardImage(this, "DivideAndConquer.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        int slimecount = SlimeHelper.GetCidEnergy();

        if (slimecount > 0) {
            for (int i = 0; i < slimecount; i++) {
                AbstractDungeon.actionManager.addToBottom(new DamageRandomEnemyAction(new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

            }
        }


        addToBot(new EnergyToCidAction(slimecount * -1));


    }

    public AbstractCard makeCopy() {
        return new DivideAndConquer();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(5);
        }
    }
}


