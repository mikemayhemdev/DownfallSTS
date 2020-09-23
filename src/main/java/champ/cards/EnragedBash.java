package champ.cards;

import champ.actions.ModifyDamageAndMagicAction;
import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnragedBash extends AbstractChampCard {

    public final static String ID = makeID("EnragedBash");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 4;
    private static final int MAGIC = 2;

    public EnragedBash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseCool = cool = 2;
        myHpLossCost = magicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        loseHP(magicNumber);
        applyToSelf(new ResolvePower(magicNumber));
        if (bcombo())
            atb(new ModifyDamageAndMagicAction(uuid, cool));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeCool(2);
    }
}