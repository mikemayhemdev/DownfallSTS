package automaton.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Macro extends AbstractBronzeCard {

    public final static String ID = makeID("Macro");

    //stupid intellij stuff skill, none, uncommon

    public Macro() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
    }

    @java.lang.Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @java.lang.Override
    public void triggerWhenDrawn() {

    }

    @java.lang.Override
    public void triggerOnManualDiscard() {
        if (upgraded) {

        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}