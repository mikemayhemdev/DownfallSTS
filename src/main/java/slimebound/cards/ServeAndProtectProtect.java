package slimebound.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import slimebound.SlimeboundMod;
import slimebound.actions.FormABlockadeAction;
import slimebound.orbs.SpawnedSlime;
import slimebound.patches.AbstractCardEnum;


public class ServeAndProtectProtect extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:ServeAndProtectProtect";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/protect.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }

    public ServeAndProtectProtect() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);


        this.baseBlock = 8;
        this.magicNumber = this.baseMagicNumber = 2;
        this.exhaust = true;
        this.selfRetain = true;
    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        int slimecount = 0;
        for (AbstractOrb o : player.orbs) {

            if (o instanceof SpawnedSlime) {
                slimecount++;
                if (upgraded) slimecount++;
            }

        }

        this.baseBlock = 8 + slimecount;
        if (slimecount > 0) this.isBlockModified = true;

        return tmp;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new FormABlockadeAction(this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new ServeAndProtectProtect();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(1);
        }
    }
}


