package guardian.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;
import guardian.GuardianMod;
import guardian.patches.AbstractCardEnum;


public class MassSlam extends AbstractGuardianCard {
    public static final String ID = GuardianMod.makeID("MassSlam");
    public static final String NAME;
    public static final String IMG_PATH = "cards/syncStrike.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    private static final int DAMAGE = 1;

    //TUNING CONSTANTS
    private static final int UPGRADE_MULTICOUNT = 2;
    private static final int MULTICOUNT = 10;
    public static String DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;

    //END TUNING CONSTANTS

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    }


    public MassSlam() {
        super(ID, NAME, GuardianMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.GUARDIAN, RARITY, TARGET);

        this.baseDamage = DAMAGE;
        this.tags.add(GuardianMod.MULTIHIT);

        this.magicNumber = this.baseMagicNumber = MULTICOUNT;
        //this.sockets.add(GuardianMod.socketTypes.RED);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY, 10), 0.25f));
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.FIRE;
            switch (new Random().random(2)) {
                case 0:
                    effect = AbstractGameAction.AttackEffect.FIRE;
                    break;
                case 1:
                    effect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
                    break;
                case 2:
                    effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;
                    break;
            }
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), effect));

        }

        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            if (AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount >= 3) {
                this.exhaust = true;
            }
        }

    }

    public AbstractCard makeCopy() {
        return new MassSlam();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MULTICOUNT);

        }


    }
}


