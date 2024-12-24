package expansioncontent.cards;


import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import expansioncontent.expansionContentMod;

import static expansioncontent.expansionContentMod.loadJokeCardImage;

public class GuardianWhirl extends AbstractExpansionCard {
    public final static String ID = makeID("GuardianWhirl");

    private static final int DAMAGE = 4;
    private static final int MAGIC = 3;

    public GuardianWhirl() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_guardian.png", "expansioncontentResources/images/1024/bg_boss_guardian.png");

        tags.add(expansionContentMod.STUDY_GUARDIAN);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = MAGIC;
        this.exhaust = true;
        loadJokeCardImage(this, "GuardianWhirl.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new SFXAction("ATTACK_WHIRLWIND"));

        for (int i = 0; i < magicNumber; i++) {
            atb(new SFXAction("ATTACK_HEAVY"));

            atb(new VFXAction(p, new CleaveEffect(), 0.1F));
            atb(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));
            //atb(new WaitAction(0.1f));
        }

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.currentBlock < 10) {
            cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

}