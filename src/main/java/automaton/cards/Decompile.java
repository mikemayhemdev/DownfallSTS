package automaton.cards;

import automaton.FunctionHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Decompile extends AbstractBronzeCard {

    public final static String ID = makeID("Decompile");

    //stupid intellij stuff skill, self, uncommon

    public Decompile() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        FunctionHelper.genPreview();
                    }
                });
                for (AbstractCard q : FunctionHelper.held.group) {
                    addToTop(new DrawCardAction(1));
                    addToTop(new GainEnergyAction(1));
                    addToTop(new ExhaustSpecificCardAction(q, FunctionHelper.held));
                }
            }
        });
    }

    public void upp() {
        selfRetain = true;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}