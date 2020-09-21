package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Encircle extends AbstractChampCard {

    public final static String ID = makeID("Encircle");

    //stupid intellij stuff attack, all_enemy, uncommon

    private static final int DAMAGE = 3;
    private static final int MAGIC = 2;

    public Encircle() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        loseHP(1);
        applyToSelf(new ResolvePower(1));
        for (int i = 0; i < magicNumber; i++) {
            atb(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            loseHP(1);
            applyToSelf(new ResolvePower(1));
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}