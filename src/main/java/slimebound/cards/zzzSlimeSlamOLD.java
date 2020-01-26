package slimebound.cards;



import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;


public class zzzSlimeSlamOLD extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:RollThrough";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "cards/bodyblow.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int UPGRADE_BONUS = 3;


    public zzzSlimeSlamOLD() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = 9;


    }


    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        if ((p != null) && (m != null)) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new IronWaveEffect(p.hb.cX, p.hb.cY, m.hb.cX), 0.5F));
        }
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, com.megacrit.cardcrawl.cards.DamageInfo.DamageType.NORMAL), com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction(p, p));
        AbstractDungeon.effectsQueue.add(new HbBlockBrokenEffect(p.hb.cX, p.hb.cY));

    }


    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {


        int bonus = 0;

        if (upgraded) {
            bonus = player.currentBlock * 2;
        } else {
            bonus = player.currentBlock;
        }

        if (bonus > 0) {
            this.isDamageModified = true;
        }
        return tmp + bonus;
    }


    public AbstractCard makeCopy() {

        return new zzzSlimeSlamOLD();

    }


    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();

        }

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }
}


