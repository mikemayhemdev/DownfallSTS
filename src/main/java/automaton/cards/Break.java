package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static sneckomod.SneckoMod.getRandomStatus;

public class Break extends AbstractBronzeCard {

    public final static String ID = makeID("Break");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 3;

    public Break() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 4;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            for (int i = 0; i < magicNumber; i++) {
                AbstractCard q = getRandomStatus().makeStatEquivalentCopy();
                shuffleIn(q, 1);
            }
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(-1);
    }
}