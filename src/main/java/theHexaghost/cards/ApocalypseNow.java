package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.AdvanceUntilInfernoAction;
import theHexaghost.ghostflames.InfernoGhostflame;

public class ApocalypseNow extends AbstractHexaCard {

    public final static String ID = makeID("ApocalypseNow");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    public ApocalypseNow() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        isEthereal = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!(GhostflameHelper.activeGhostFlame instanceof InfernoGhostflame))
            atb(new AdvanceUntilInfernoAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}