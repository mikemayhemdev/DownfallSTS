package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import downfall.util.CardIgnore;
import expansioncontent.expansionContentMod;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeProjectileEffect;

public class GoopSpray extends AbstractExpansionCard {
    public final static String ID = makeID("SuperGoopSpray");

    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;
    private static final int downfallMagic = 1;
    private static final int UPGRADE_downfallMagic = 1;

    public GoopSpray() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_slime.png", "expansioncontentResources/images/1024/bg_boss_slime.png");

        tags.add(expansionContentMod.STUDY_SLIMEBOSS);
        tags.add(expansionContentMod.STUDY);

        baseDownfallMagic = downfallMagic;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying)) {
                    atb(new VFXAction(new SlimeProjectileEffect(p.hb.cX, p.hb.cY, monster.hb.cX, monster.hb.cY, 3F, false, 0.6F), 0.01F));


                }
                atb(new WaitAction(0.2F));
            }
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying)) {

                    atb(new ApplyPowerAction(monster, p, new SlimedPower(monster, p, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    int vuln = downfallMagic;
                    atb(new ApplyPowerAction(monster, p, new WeakPower(monster, vuln, false), vuln, true, AbstractGameAction.AttackEffect.NONE));


                }

            }
        }


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeDownfall(UPGRADE_downfallMagic);
        }
    }

}