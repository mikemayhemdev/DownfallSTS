package automaton.cards;

import automaton.cardmods.EncodeAndShuffleMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Replicate extends AbstractBronzeCard {

    public final static String ID = makeID("Replicate");

    //stupid intellij stuff attack, enemy, basic

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public Replicate() {
        super(ID, 0, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        CardModifierManager.addModifier(this, new EncodeAndShuffleMod());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}