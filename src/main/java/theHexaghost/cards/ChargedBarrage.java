package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;

public class ChargedBarrage extends AbstractHexaCard {

    public final static String ID = makeID("ChargedBarrage");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON


    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public ChargedBarrage() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseBurn = burn = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        burn(m, burn);
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            if (gf.charged) {
                burn(m, burn);
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(UPG_MAGIC);
        }
    }
}