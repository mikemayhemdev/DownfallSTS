package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class QuantumStrike extends AbstractTimeEaterCard {
    public final static String ID = makeID("QuantumStrike");
    // intellij stuff attack, enemy, special, 6, 2, , , , 

    public QuantumStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for (AbstractCard c : p.drawPile.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (AbstractCard c : p.exhaustPile.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (AbstractCard c : SuspendHelper.suspendGroup.group) {
            if (c instanceof QuantumStrike) {
                count++;
            }
        }
        for (int i = 0; i < count; i++) dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        rawDescription = cardStrings.DESCRIPTION;
        rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void upp() {
        upgradeDamage(2);
    }
}