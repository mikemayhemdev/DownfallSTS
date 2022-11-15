package champ.cards;

import champ.ChampMod;
import champ.powers.CounterPower;
import champ.powers.UltimateFormPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class GutPunch extends AbstractChampCard {

    public final static String ID = makeID("GutPunch");

    public GutPunch() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 8;
       
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //berserkOpen();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        applyToSelf(new CounterPower(1));
    }

    public void upp() {
        upgradeDamage(2);
    }


}