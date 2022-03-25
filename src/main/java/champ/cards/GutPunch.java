package champ.cards;

import champ.ChampMod;
import champ.powers.UltimateFormPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class GutPunch extends AbstractChampCard {

    public final static String ID = makeID("GutPunch");

    public GutPunch() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBOBERSERKER);
        postInit();
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //berserkOpen();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        //  fatigue(2);
        if (bcombo() || dcombo()) {
            triggerOpenerRelics(AbstractDungeon.player.stance.ID.equals(NeutralStance.STANCE_ID));
            ultimateStance();
            applyToSelf(new UltimateFormPower(1));
        }
    }

    public void upp() {
        upgradeDamage(5);
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() || bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }
}