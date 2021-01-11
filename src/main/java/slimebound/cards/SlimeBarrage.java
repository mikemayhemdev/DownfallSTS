package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.TriggerSlimeAttacksAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.BuffSecondarySlimeEffectsPower;
import sneckomod.SneckoMod;


public class SlimeBarrage extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SlimeBarrage";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/slimebarrage.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public SlimeBarrage() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 8;
        this.magicNumber = this.baseMagicNumber = 2;

        //this.exhaust = true;

        //this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.3F));
        AbstractDungeon.actionManager.addToBottom(new TriggerSlimeAttacksAction(p));
        if (upgraded)
            AbstractDungeon.actionManager.addToBottom(new TriggerSlimeAttacksAction(p));

        checkMinionMaster();
    }

    public AbstractCard makeCopy() {

        return new SlimeBarrage();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();



        }

    }
}


