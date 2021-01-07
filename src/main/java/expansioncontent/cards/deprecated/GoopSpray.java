package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeProjectileEffect;

@CardIgnore
public class GoopSpray extends AbstractExpansionCard {
    public final static String ID = makeID("GoopSpray");

    private static final int MAGIC = 12;
    private static final int UPGRADE_MAGIC = 4;

    public GoopSpray() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        tags.add(expansionContentMod.STUDY_SLIMEBOSS);
        tags.add(expansionContentMod.STUDY);

        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
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
                    int vuln = 2;
                    if (upgraded) vuln++;
                    atb(new ApplyPowerAction(monster, p, new VulnerablePower(monster, vuln, false), vuln, true, AbstractGameAction.AttackEffect.NONE));


                }

            }
        }


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}