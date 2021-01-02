package champ.cards;

import champ.ChampMod;
import champ.actions.ModifyDamageAndBlockAction;
import champ.actions.PreciseThrustAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PreciseThrust extends AbstractChampCard {

    public final static String ID = makeID("PreciseThrust");

    //stupid intellij stuff attack, self_and_enemy, uncommon

    private static final int DAMAGE = 8;
    private static final int BLOCK = 4;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;

    public PreciseThrust() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBODEFENSIVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (dcombo()) {
            atb(new PreciseThrustAction(m, makeInfo()));
        } else {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        }

    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}