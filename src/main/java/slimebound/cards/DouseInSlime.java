package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
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
import slimebound.powers.PreventSlimeDecayPower;
import slimebound.powers.SlimedPower;
import slimebound.vfx.FakeFlashAtkImgEffect;
import slimebound.vfx.SlimeProjectileEffect;


public class DouseInSlime extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DouseInSlime";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/douseinslime.png";
    public static final Logger logger = LogManager.getLogger(SlimeboundMod.class.getName());
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 3;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public DouseInSlime() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.slimed = this.baseSlimed = 14;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeProjectileEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY - 30, 5F, false, .8F, false, true), 0.6F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FakeFlashAtkImgEffect(m.hb.cX, m.hb.cY, Color.PURPLE, 2F, false, .4F)));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlimedPower(m, p, this.slimed), this.slimed, true, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PreventSlimeDecayPower(m, p, 1), 1, true, AbstractGameAction.AttackEffect.NONE));


    }

    public AbstractCard makeCopy() {

        return new DouseInSlime();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeBaseCost(2);

        }

    }
}


