package automaton.cards;

import automaton.AutomatonMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.EtherealMod;

public class DigitalCarnage extends AbstractBronzeCard {

    public final static String ID = makeID("DigitalCarnage");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 20;
    private static final int UPG_DAMAGE = 8;

    public DigitalCarnage() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        isEthereal = true;
        baseDamage = DAMAGE;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay, int count) {
        super.onCompile(function, forGameplay, count);
        CardModifierManager.addModifier(function, new EtherealMod());
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}