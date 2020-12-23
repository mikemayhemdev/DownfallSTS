package champ.cards;

import champ.ChampMod;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.fatigue;

public class GutPunch extends AbstractChampCard {

    public final static String ID = makeID("GutPunch");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 1;

    private static final int MAGIC = 5;
    private static final int UPG_MAGIC = 5;

    public GutPunch() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.OPENER);
        myHpLossCost = 0;
        tags.add(ChampMod.OPENERBERSERKER);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        berserkOpen();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
      //  fatigue(2);
        if (dcombo()) {
           // exhaust = true;
            fatigue(magicNumber);
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
       // myHpLossCost++;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (dcombo()){
            myHpLossCost = magicNumber;
        } else {
            myHpLossCost = 0;
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
}