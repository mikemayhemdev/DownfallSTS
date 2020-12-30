package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedThornsPower;
import slimebound.vfx.FakeFlashAtkImgEffect;
import slimebound.vfx.SlimeDripsEffectPurple;
import slimebound.vfx.SlimeProjectileEffect;


public class LivingWall extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:LivingWall";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/slimewave.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
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


    public LivingWall() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.slimed = this.baseSlimed = 4;
        //this.baseBlock = 2;

        this.baseBlock = 12;

        //  this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeProjectileEffect(p.hb.cX, p.hb.cY, p.hb.cX + 275 * Settings.scale, p.hb.cY - 40 * Settings.scale, 2F, false, 0.6F, false, false), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeProjectileEffect(p.hb.cX, p.hb.cY, p.hb.cX + 275 * Settings.scale, p.hb.cY, 2F, false, 0.6F, true, false), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeProjectileEffect(p.hb.cX, p.hb.cY, p.hb.cX + 275 * Settings.scale, p.hb.cY + 40 * Settings.scale, 2F, false, 0.6F, true, false), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeProjectileEffect(p.hb.cX, p.hb.cY, p.hb.cX + 275 * Settings.scale, p.hb.cY + 80 * Settings.scale, 2F, false, 0.6F, true, false), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeProjectileEffect(p.hb.cX, p.hb.cY, p.hb.cX + 275 * Settings.scale, p.hb.cY + 120 * Settings.scale, 2F, false, 0.6F, true, false), 0.325F));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FakeFlashAtkImgEffect(p.hb.cX + 275 * Settings.scale, p.hb.cY - 30 * Settings.scale, Color.PURPLE, 1F, true, .4F), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FakeFlashAtkImgEffect(p.hb.cX + 275 * Settings.scale, p.hb.cY, Color.PURPLE, 1F, false, .4F), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FakeFlashAtkImgEffect(p.hb.cX + 275 * Settings.scale, p.hb.cY + 50 * Settings.scale, Color.PURPLE, 1F, false, .4F), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FakeFlashAtkImgEffect(p.hb.cX + 275 * Settings.scale, p.hb.cY + 90 * Settings.scale, Color.PURPLE, 1F, false, .4F), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new FakeFlashAtkImgEffect(p.hb.cX + 275 * Settings.scale, p.hb.cY + 130 * Settings.scale, Color.PURPLE, 1F, false, .4F), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 290 * Settings.scale, p.hb.cY - 40 * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 290 * Settings.scale, p.hb.cY * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 290 * Settings.scale, p.hb.cY + 40 * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 290 * Settings.scale, p.hb.cY + 80 * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 290 * Settings.scale, p.hb.cY + 120 * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 310 * Settings.scale, p.hb.cY - 30 * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 310 * Settings.scale, p.hb.cY + 10 * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 310 * Settings.scale, p.hb.cY + 50 * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 310 * Settings.scale, p.hb.cY + 80 * Settings.scale, 4), 0.0f));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new SlimeDripsEffectPurple(p.hb.cX + 310 * Settings.scale, p.hb.cY + 130 * Settings.scale, 4), 0.0f));

        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SlimedThornsPower(p, p, this.slimed), this.slimed));

    }

    public AbstractCard makeCopy() {

        return new LivingWall();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeSlimed(2);
            upgradeBlock(3);

            //upgradeBlock(1);

        }

    }
}


