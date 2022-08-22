package automaton.cards;

import automaton.AutomatonMod;
import automaton.cardmods.PlayMeTwiceCardmod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TerminatorRepeatCard extends AbstractBronzeCard {

    public final static String ID = makeID("TerminatorRepeatCard");

    //stupid intellij stuff skill, self, common


    public TerminatorRepeatCard() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (lastCard()) {
            CardModifierManager.addModifier(function, new PlayMeTwiceCardmod());
        }
    }

    public void upp() {

    }

}