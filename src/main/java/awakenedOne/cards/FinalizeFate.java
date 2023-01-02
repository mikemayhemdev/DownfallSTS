package awakenedOne.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;

public class FinalizeFate extends AbstractAwakenedCard {
    public final static String ID = makeID(FinalizeFate.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 11, 3, , , , 

    public FinalizeFate() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 9;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new DiscardAction(p, p, BaseMod.MAX_HAND_SIZE, true));
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                if (EnergyPanel.totalCount > 0) {
                    dmgTop(m, AttackEffect.SLASH_HORIZONTAL);
                }
            }
        });
    }

    public void upp() {
        upgradeDamage(3);
    }
}