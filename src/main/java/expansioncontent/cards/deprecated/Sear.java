package expansioncontent.cards.deprecated;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import slimebound.vfx.SearEffect;
import theHexaghost.powers.BurnPower;

@CardIgnore
public class Sear extends AbstractExpansionCard {
    public final static String ID = makeID("Sear");

    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int MAGIC = 12;
    private static final int UPGRADE_MAGIC = 2;

    public Sear() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        tags.add(expansionContentMod.STUDY_HEXAGHOST);
        tags.add(expansionContentMod.STUDY);

        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new VFXAction(new SearEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.4F));

        atb(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        atb(new ApplyPowerAction(m, p, new BurnPower(m, magicNumber), magicNumber));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(2);
            upgradeMagicNumber(2);
        }
    }

}