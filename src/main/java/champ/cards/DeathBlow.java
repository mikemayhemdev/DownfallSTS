package champ.cards;

import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DeathBlow extends AbstractChampCard {

    public final static String ID = makeID("DeathBlow");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = -1;
    private static final int MAGIC = 30;
    private static final int UPG_MAGIC = 10;

    public DeathBlow() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        fatigue(magicNumber);
        applyToSelf(new ResolvePower(magicNumber));
        baseDamage = magicNumber;
        calculateCardDamage(null);
        allDmg(AbstractGameAction.AttackEffect.SLASH_HEAVY);
    }

    //TODO: same thing, damage displayer here (additionally, hp cost display also needs dynamic updating at the same time as the damage one)

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}