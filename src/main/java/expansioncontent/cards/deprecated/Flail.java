package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

@CardIgnore
public class Flail extends AbstractExpansionCard {
    public final static String ID = makeID("Flail");

    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 1;


    public Flail() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        tags.add(expansionContentMod.STUDY_AUTOMATON);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new SFXAction("ATTACK_WHIRLWIND"));

        for (int i = 0; i < 2; i++) {
            atb(new SFXAction("ATTACK_HEAVY"));

            atb(new VFXAction(p, new CleaveEffect(), 0.1F));
            atb(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
            //atb(new WaitAction(0.1F));


        }
        atb(new ApplyPowerAction(p, p, new ArtifactPower(p, this.magicNumber), this.magicNumber));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(1);
        }
    }

}
