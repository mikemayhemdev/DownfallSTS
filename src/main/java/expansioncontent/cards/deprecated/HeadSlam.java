/*
package expansioncontent.cards;



import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;

public class HeadSlam extends AbstractExpansionCard {
    public final static String ID = makeID("HeadSlam");

    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public HeadSlam() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.BossCrystalImpactEffect(m.hb.cX, m.hb.cY), 0.5F));

        if (m.type != AbstractMonster.EnemyType.BOSS) {
            atb(new StunMonsterAction(m,p,1));

        } else {
            atb(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        }

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

}



*/