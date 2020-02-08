package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import expansioncontent.actions.RandomCardWithTagAction;
import expansioncontent.expansionContentMod;


public class DashGenerateEvil extends AbstractExpansionCard {
    public final static String ID = makeID("DashGenerateEvil");

    private static final int BLOCK = 12;
    private static final int UPGRADE_BLOCK = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 2;

    public DashGenerateEvil() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);

        baseBlock = BLOCK;
        baseDamage = DAMAGE;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        this.addToBot(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));

        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        atb(new GainBlockAction(p, p, this.block));

        atb(new RandomCardWithTagAction(upgraded, expansionContentMod.STUDY, true));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeDamage(UPGRADE_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}

