package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WhirlingStrike extends AbstractBronzeCard {

    public final static String ID = makeID("WhirlingStrike");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public WhirlingStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        thisEncodes();
        isMultiDamage = true;
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}