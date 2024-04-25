package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.CrispyPower_new;

public class ExtraCrispy extends AbstractHexaCard {

    public final static String ID = makeID("ExtraCrispy");

    private static final int MAGIC = 1;
    // private static final int UPG_MAGIC = 1;

    public ExtraCrispy() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        HexaMod.loadJokeCardImage(this, "ExtraCrispy.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new CrispyPower_new(this.magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override //zhs card text thing
    public void initializeDescriptionCN() {
        super.initializeDescriptionCN();
        if(Settings.language == Settings.GameLanguage.ZHS && this.description!=null && this.description.size()>=1 ) {
            for(int i = 0; i < this.description.size(); i++){
                if( this.description.get(i).text.equals("，") ){
                    StringBuilder sb = new StringBuilder();
                    this.description.get(i-1).text = sb.append(this.description.get(i-1).text).append("，").toString();
                    this.description.remove(i);
                }
            }
        }
    }
}
