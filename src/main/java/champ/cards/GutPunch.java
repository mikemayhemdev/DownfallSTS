package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GutPunch extends AbstractChampCard {

    public final static String ID = makeID("GutPunch");

    public GutPunch() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 6;
        baseMagicNumber = magicNumber = 1;
        tags.add(ChampMod.OPENER);
        tags.add(ChampMod.OPENERBERSERKER);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        berserkOpen();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        //  fatigue(2);
        if (dcombo()) {
            // exhaust = true;
            atb(new GainEnergyAction(1));
            atb(new DrawCardAction(magicNumber));
        }
    }

    public void upp() {
        upgradeDamage(3);
        upgradeMagicNumber(1);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
}