package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.BurnAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ChargedBarrage extends AbstractHexaCard {

    public final static String ID = makeID("ChargedBarrage");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON


    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public ChargedBarrage() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new BurnAction(m, magicNumber));
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            if (gf.charged) {
                atb(new BurnAction(m, magicNumber));
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}