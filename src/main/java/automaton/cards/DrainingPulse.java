package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DrainingPulse extends AbstractBronzeCard {

    public final static String ID = makeID("DrainingPulse");

    //stupid intellij stuff skill, all_enemy, basic

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public DrainingPulse() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(AutomatonMod.BLASTER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster q : monsterList()) {
            applyToEnemy(q, autoWeak(q, 1));
        }
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}