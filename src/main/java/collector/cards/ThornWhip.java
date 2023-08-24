package collector.cards;

import collector.util.EssenceSystem;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class ThornWhip extends AbstractCollectorCard implements OnObtainCard {
    public final static String ID = makeID(ThornWhip.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 11, 1, , , , 

    public ThornWhip() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 12;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    public void upp() {
        upgradeDamage(5);
    }

    @Override
    public void onObtainCard() {
        EssenceSystem.changeEssence(9);
    }
}