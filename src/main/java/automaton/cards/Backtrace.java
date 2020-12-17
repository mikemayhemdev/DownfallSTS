package automaton.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.util.ExhaustMod;

public class Backtrace extends AbstractBronzeCard {

    public final static String ID = makeID("Backtrace");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 11;
    private static final int UPG_DAMAGE = 4;

    public Backtrace() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        isInnate = true;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        super.onCompile(function, forGameplay);
        CardModifierManager.addModifier(function, new ExhaustMod());
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}