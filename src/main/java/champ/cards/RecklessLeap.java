package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RecklessLeap extends AbstractChampCard {

    public final static String ID = makeID("RecklessLeap");

    //stupid intellij stuff attack, self_and_enemy, uncommon

    private static final int DAMAGE = 18;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 15;
    private static final int UPG_MAGIC = 5;

    public RecklessLeap() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
        myHpLossCost = MAGIC;
        tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        fatigue(magicNumber);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
        myHpLossCost = magicNumber;
    }
}