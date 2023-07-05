package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;

public class FlameLash extends AbstractCollectorCard implements OnPyreCard {
    public final static String ID = makeID(FlameLash.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 2, , , 8, 2

    public FlameLash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8;
        isPyre();
    }

    private int toAdd = -1;

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        AbstractCard inst = this;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (toAdd > 0) {
                    addToTop(new ModifyDamageAction(inst.uuid, toAdd));
                }
            }
        });
    }

    @Override
    public void onPyred(AbstractCard card) {
        if (card.baseDamage > 0) {
            toAdd = card.baseDamage;
        } else {
            toAdd = -1;
        }
    }

    public void upp() {
        upgradeDamage(4);
    }
}