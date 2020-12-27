package automaton.cards;

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

    private static final int BLOCK = 13;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = -1;

    public Safeguard() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            applyToSelf(new FrailPower(AbstractDungeon.player, 2, false));
        }
    }

    public void upp() {
        upgradeBlock(4);
    }
}