package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeProjectileEffect;


public class CorrosiveSpit extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:CorrosiveSpit";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/corrosivespit.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
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


    public CorrosiveSpit() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.slimed = this.baseSlimed = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeProjectileEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY, 2F, false, 0.6F), 0.3F));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlimedPower(m, p, this.slimed), this.slimed, true, AbstractGameAction.AttackEffect.NONE));


    }

    public AbstractCard makeCopy() {

        return new CorrosiveSpit();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeBaseCost(0);

        }

    }
}


