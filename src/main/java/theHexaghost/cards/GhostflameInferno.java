package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.ChargeCurrentFlameAction;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.InfernoGhostflame;

public class GhostflameInferno extends AbstractHexaCard {

    public final static String ID = makeID("GhostflameInferno");

    //stupid intellij stuff ATTACK, ALL_ENEMY, RARE

    public GhostflameInferno() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        selfRetain = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) atb(new ChargeCurrentFlameAction());
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
                    if (gf instanceof InfernoGhostflame) {
                        GhostflameHelper.hexaGhostFlames.get(GhostflameHelper.hexaGhostFlames.indexOf(gf)).charge();
                    }
                }
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}