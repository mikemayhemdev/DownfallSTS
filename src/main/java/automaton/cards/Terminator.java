package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Terminator extends AbstractBronzeCard {

    public final static String ID = makeID("Terminator");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public Terminator() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        thisEncodes();
        tags.add(AutomatonMod.SPECIAL_COMPILE_TEXT);
    }

    @Override
    public void onInput() {
        if (lastCard()) {
            int x = magicNumber;
            baseMagicNumber += 1;
            magicNumber += 1;
            if (x == 1) {
                rawDescription = EXTENDED_DESCRIPTION[2];
                initializeDescription();
            }
            superFlash();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            dmg(m, AbstractGameAction.AttackEffect.LIGHTNING);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}