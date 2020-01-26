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
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import slimebound.SlimeboundMod;
import slimebound.powers.AwakenedOnePower;


public class DarkVoid extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:DarkVoid";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/darkvoid.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;


    public DarkVoid() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, CardColor.COLORLESS, RARITY, TARGET);

        tags.add(SlimeboundMod.STUDY_AWAKENEDONE);
        tags.add(SlimeboundMod.STUDY);
        this.baseDamage = 7;
        this.isMultiDamage = true;
        this.exhaust = true;



    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));


        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying)) {

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new AwakenedOnePower(monster, p,1), 1, true, AbstractGameAction.AttackEffect.NONE));


                }

            }
        }

    }


    public AbstractCard makeCopy() {

        return new DarkVoid();

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


