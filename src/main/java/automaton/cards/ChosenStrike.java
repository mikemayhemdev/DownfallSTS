package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.ChosenAction;
import champ.actions.IncreaseMiscDamageAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.omg.CORBA.DATA_CONVERSION;

public class ChosenStrike extends AbstractBronzeCard {

    public final static String ID = makeID("ChosenStrike");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 3;

    public ChosenStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        thisEncodes();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        baseDamage = DAMAGE + misc;
        if (upgraded) baseDamage += UPG_DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }



    @Override
    public void onCompile(AbstractCard function, boolean forGameplay, int textLevel) {
        if (forGameplay){
            atb(new ChosenAction(this.uuid, magicNumber));
            //TODO - This doesn't happen fast enough to affect the Function, but does work on master deck's copy
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}