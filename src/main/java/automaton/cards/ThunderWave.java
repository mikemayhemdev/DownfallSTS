package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.BufferPower;

import static automaton.AutomatonMod.makeBetaCardPath;

import java.util.Iterator;

public class ThunderWave extends AbstractBronzeCard {

    public final static String ID = makeID("ThunderWave");

    //stupid intellij stuff attack, all_enemy, rare

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 4;

    public ThunderWave() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        exhaust = true;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("ThunderWave.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.LIGHTNING);
        applyToSelf(new BufferPower(p, 1));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}