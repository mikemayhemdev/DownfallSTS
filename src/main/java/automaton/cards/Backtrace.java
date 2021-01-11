package automaton.cards;

import automaton.AutomatonMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.cardmods.ExhaustMod;

public class Backtrace extends AbstractBronzeCard {

    public final static String ID = makeID("Backtrace");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public Backtrace() {
        super(ID, 0, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        isInnate = true;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new ExhaustMod());
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}