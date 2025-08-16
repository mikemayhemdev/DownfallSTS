package champ.cards;

import champ.powers.DancingMasterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;


public class DancingMaster extends AbstractChampCard {

    public final static String ID = makeID("DancingMaster");

    //stupid intellij stuff power, self, rare

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public DancingMaster() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
      //  this.tags.add(SneckoMod.BANNEDFORSNECKO);
        postInit();
        loadJokeCardImage(this, "DancingMaster.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DancingMasterPower(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(1);
    }
}