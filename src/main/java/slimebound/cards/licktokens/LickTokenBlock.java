package slimebound.cards.licktokens;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.cards.AbstractSlimeboundCard;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;
import slimebound.vfx.LickEffect;
import slimebound.vfx.SlimeDripsEffect;


public class LickTokenBlock extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:LickTokenBlock";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/blocklick.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
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

    public LickTokenBlock() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE,  CardColor.COLORLESS, RARITY, TARGET);
        tags.add(SlimeboundMod.LICK);


        this.slimed = this.baseSlimed = 4;
        this.baseBlock = 4;
        this.exhaust = true;
        SlimeboundMod.loadJokeCardImage(this, "blocklick.png");


    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.effectsQueue.add(new SlimeDripsEffect(m.hb.cX, m.hb.cY, 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlimedPower(m, p, this.slimed), this.slimed, true, AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LickEffect(m.hb.cX, m.hb.cY, 0.6F, new Color(Color.GOLDENROD)), 0.1F));

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));


    }


    public AbstractCard makeCopy() {
        return new LickTokenBlock();
    }

    public void upgrade() {
        if (!this.upgraded) {

            upgradeName();
            upgradeSlimed(4);
        }
    }
}


