package automaton.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Boost extends AbstractBronzeCard {

    public final static String ID = makeID("Boost");

    //stupid intellij stuff skill, self, uncommon

    private static final int BLOCK = 8;
    private static final int MAGIC = 2;

    public Boost() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
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
            applyToSelf(new StrengthPower(AbstractDungeon.player, magicNumber));
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}