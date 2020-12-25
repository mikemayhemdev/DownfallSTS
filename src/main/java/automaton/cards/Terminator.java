package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        thisEncodes();
    }

    @Override
    public void onCompilePreCardEffectEmbed(boolean forGameplay) {
        if (lastCard() && forGameplay) {
            baseDamage *= 2;
            damage *= 2;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.LIGHTNING);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}