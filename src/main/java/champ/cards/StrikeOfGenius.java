package champ.cards;

import champ.powers.StrikeOfGeniusPower;
import champ.powers.StrikeOfGeniusUpgradedPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;

public class StrikeOfGenius extends AbstractChampCard {

    public final static String ID = makeID("StrikeOfGenius");

    //stupid intellij stuff power, self, rare

    public StrikeOfGenius() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);

     //   this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) applyToSelf(new StrikeOfGeniusUpgradedPower(1));
        else
            applyToSelf(new StrikeOfGeniusPower(1));
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}