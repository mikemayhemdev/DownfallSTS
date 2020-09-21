package champ.cards;

import champ.ChampMod;
import champ.powers.EntangleNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SwordThrow extends AbstractChampCard {

    public final static String ID = makeID("SwordThrow");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 10;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public SwordThrow() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        for (int i = 0; i < magicNumber; i++) dmg(m, AbstractGameAction.AttackEffect.SMASH);
        if (!bcombo()) applyToSelf(new EntangleNextTurnPower(1));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}