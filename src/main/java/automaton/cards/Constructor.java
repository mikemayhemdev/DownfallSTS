package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Constructor extends AbstractBronzeCard {

    public final static String ID = makeID("Constructor");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    public Constructor() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = BLOCK;
        thisEncodes();
        tags.add(AutomatonMod.SPECIAL_COMPILE_TEXT);
    }

    @Override
    public void onInput() {
        if (firstCard()) {
            this.baseBlock += magicNumber;
            this.block += magicNumber;
            superFlash();
        }
    }

    @Override
    public String getNoun() {
        if (firstCard()) {
            return EXTENDED_DESCRIPTION[2];
        }
        return super.getNoun();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
        upgradeMagicNumber(UPG_BLOCK);
    }
}