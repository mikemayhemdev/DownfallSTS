package automaton.cards;

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
        thisEncodes();
        //TODO - Only show ocmpile text if its the first card in the sequence, saying "Constructor will double its block"
    }

    @Override
    public void onCompilePreCardEffectEmbed(boolean forGameplay) {
        if (firstCard() && forGameplay) {
            this.baseBlock *= 2;
            this.block *= 2;
            superFlash();
        }
    }

    @Override
    public String getNoun() {
        return position == 0 ? EXTENDED_DESCRIPTION[1] : EXTENDED_DESCRIPTION[2];
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    public void upp() {
        upgradeBlock(UPG_BLOCK);
    }
}