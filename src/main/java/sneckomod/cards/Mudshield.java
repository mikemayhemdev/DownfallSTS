package sneckomod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.powers.MudshieldPower;

public class Mudshield extends AbstractSneckoCard {

    public final static String ID = makeID("Mudshield");

    //stupid intellij stuff POWER, SELF, RARE

    public Mudshield() {
        super(ID,  2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new MudshieldPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isInnate = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}