package expansioncontent.cards.deprecated;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import expansioncontent.powers.AwakenedOnePower;

@CardIgnore
public class DarkVoid extends AbstractExpansionCard {
    public final static String ID = makeID("DarkVoid");

    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 4;

    public DarkVoid() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        tags.add(expansionContentMod.STUDY_AWAKENEDONE);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, new Color(0.1F, 0.0F, 0.2F, 1.0F), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        atb(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
        atb(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));


        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying)) {

                    atb(new ApplyPowerAction(monster, p, new AwakenedOnePower(monster, p, 1), 1, true, AbstractGameAction.AttackEffect.NONE));


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
