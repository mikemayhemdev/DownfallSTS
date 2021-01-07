package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import guardian.vfx.BronzeOrbEffect;

@CardIgnore
public class BronzeBeam extends AbstractExpansionCard {
    public final static String ID = makeID("BronzeBeam");

    private static final int BLOCK = 14;
    private static final int UPGRADE_BLOCK = 3;
    private static final int DAMAGE = 14;
    private static final int UPGRADE_DAMAGE = 3;

    public BronzeBeam() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        tags.add(expansionContentMod.STUDY_AUTOMATON);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        baseDamage = DAMAGE;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        atb(new VFXAction(new BronzeOrbEffect(p, m), 0.5F));

        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        atb(new GainBlockAction(p, p, this.block));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            upgradeDamage(UPGRADE_DAMAGE);
        }
    }

}

