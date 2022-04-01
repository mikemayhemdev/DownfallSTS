package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import guardian.powers.ExhaustStatusesPower;

public class Safeguard extends AbstractBronzeCard {

    public final static String ID = makeID("Safeguard");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 7;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public Safeguard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(2);
    }
}