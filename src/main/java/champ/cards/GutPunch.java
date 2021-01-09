package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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
        tags.add(ChampMod.OPENERBERSERKER);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        baseBlock = block = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        berserkOpen();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        blck();
        //  fatigue(2);
        if (dcombo()) {
            // exhaust = true;
            atb(new GainEnergyAction(1));
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
        // myHpLossCost++;
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
}