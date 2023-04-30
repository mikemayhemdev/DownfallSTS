package collector.cards;

import basemod.helpers.CardModifierManager;
import collector.cardmods.PyreMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;
import static com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE;

public class Fireball extends AbstractCollectorCard {
    public final static String ID = makeID(Fireball.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Fireball() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        baseMagicNumber = magicNumber = 4;
        isPyre();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, FIRE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                upgradeDamage(magicNumber);
            }
        });
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}