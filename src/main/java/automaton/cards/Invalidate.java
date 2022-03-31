package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class Invalidate extends AbstractBronzeCard {

    public final static String ID = makeID("Invalidate");

    //stupid intellij stuff skill, self, common

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public Invalidate() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, autoVuln(m, magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}