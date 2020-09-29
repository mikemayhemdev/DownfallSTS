package champ.cards;

import champ.actions.ModifyDamageAndBlockAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PreciseThrust extends AbstractChampCard {

    public final static String ID = makeID("PreciseThrust");

    //stupid intellij stuff attack, self_and_enemy, uncommon

    private static final int DAMAGE = 4;
    private static final int BLOCK = 4;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 2;

    public PreciseThrust() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        blck();
        if (gcombo())
            atb(new ModifyDamageAndBlockAction(uuid, magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = gcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}