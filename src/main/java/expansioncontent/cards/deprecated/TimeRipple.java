package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

@CardIgnore
public class TimeRipple extends AbstractExpansionCard {
    public final static String ID = makeID("TimeRipple");

    private static final int DAMAGE = 18;
    private static final int UPGRADE_DAMAGE = 6;

    public TimeRipple() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        tags.add(expansionContentMod.STUDY_TIMEEATER);
        tags.add(expansionContentMod.STUDY);
        this.magicNumber = this.baseMagicNumber = 1;
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new com.megacrit.cardcrawl.actions.animations.VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.5F));
        atb(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying)) {

                    atb(new ApplyPowerAction(monster, p, new SlowPower(monster, this.magicNumber), 10 * this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));


                }

            }
        }


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

}

