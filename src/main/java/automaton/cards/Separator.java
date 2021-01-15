package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Separator extends AbstractBronzeCard {

    public final static String ID = makeID("Separator");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public Separator() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = DAMAGE;
        thisEncodes();
        tags.add(AutomatonMod.SPECIAL_COMPILE_TEXT);
    }

    @Override
    public void onInput() {
        if (!firstCard() && !lastCard()) {
            baseDamage += magicNumber;
            damage += magicNumber;
            superFlash();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_DAMAGE);
    }
}