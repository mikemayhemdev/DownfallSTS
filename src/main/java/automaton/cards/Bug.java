package automaton.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Bug extends AbstractBronzeCard {

    public final static String ID = makeID("Bug");

    //stupid intellij stuff skill, self, uncommon

    public Bug() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        AutoplayField.autoplay.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(1));
        //TODO: Fire a random card in any Mecha Pile - also how does this upgrade?
    }

    public void upp() {
    }
}