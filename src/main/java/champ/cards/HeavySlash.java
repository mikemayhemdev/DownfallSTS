package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HeavySlash extends AbstractChampCard {

    public final static String ID = makeID("HeavySlash");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 20;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;

    public HeavySlash() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        finisher();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if (strength != null) {
            strength.amount *= this.magicNumber;
        }
        super.applyPowers();
        if (strength != null) {
            strength.amount /= this.magicNumber;
        }
    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        if (strength != null) {
            strength.amount *= this.magicNumber;
        }
        super.calculateCardDamage(mo);
        if (strength != null) {
            strength.amount /= this.magicNumber;
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}