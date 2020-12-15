package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class BoomerangBolt extends AbstractBronzeCard {

    public final static String ID = makeID("BoomerangBolt");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 18;
    private static final int UPG_DAMAGE = 6;

    public BoomerangBolt() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        tags.add(AutomatonMod.BLASTER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(p, new CleaveEffect(), 0.0F));
        allDmg(AbstractGameAction.AttackEffect.NONE);
        atb(new ReduceCostAction(this));
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}