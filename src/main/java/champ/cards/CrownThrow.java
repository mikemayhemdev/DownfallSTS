package champ.cards;

import champ.ChampMod;
import champ.powers.DrawLessNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrownThrow extends AbstractChampCard {

    public final static String ID = makeID("CrownThrow");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 14;
    private static final int UPG_DAMAGE = 4;

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = -1;

    public CrownThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        //tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        if (!gcombo()) applyToSelf(new DrawLessNextTurnPower(magicNumber));
       // finisher();
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = gcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}