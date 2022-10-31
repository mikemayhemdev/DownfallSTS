package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;

@CardIgnore
public class FaceSlap extends AbstractExpansionCard {
    public final static String ID = makeID("FaceSlap");

    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int MAGIC = 2;
    private static final int UPGRADE_MAGIC = 1;

    public FaceSlap() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new RemoveAllBlockAction(m, p));
        atb(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        atb(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }

}

