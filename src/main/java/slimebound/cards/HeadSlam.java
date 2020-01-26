package slimebound.cards;



import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;


public class HeadSlam extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:HeadSlam";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/headslam.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;


    public HeadSlam() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);
        tags.add(SlimeboundMod.STUDY_TIMEEATER);
        tags.add(SlimeboundMod.STUDY);

        this.baseDamage = 6;

        this.exhaust = true;


    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.BossCrystalImpactEffect(m.hb.cX, m.hb.cY), 0.5F));

        if (m.type != AbstractMonster.EnemyType.BOSS) {
            AbstractDungeon.actionManager.addToBottom(new StunMonsterAction(m,p,1));

        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        }

    }


    public AbstractCard makeCopy() {

        return new HeadSlam();

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

    }
}


