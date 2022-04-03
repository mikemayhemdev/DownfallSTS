package timeeater.cards;

import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Duality extends AbstractTimeEaterCard {
    public final static String ID = makeID("Duality");
    // intellij stuff skill, self, rare, , , , , , 1

    public Duality() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                    if (q.type == CardType.ATTACK) {
                        AbstractCard q2 = q.makeStatEquivalentCopy();
                        q2.updateCost(-magicNumber);
                        CardModifierManager.addModifier(q2, new ExhaustMod());
                        att(new SuspendAction(q2));
                    }
                }
            }
        });
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}