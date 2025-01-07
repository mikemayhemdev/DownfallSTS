package sneckomod.cards;

import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.actions.ReduceCostActionFixed;
import sneckomod.SneckoMod;


public class DiceBoulder extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBoulder");
    private static final int BASE_BLOCK = 7;
    private static final int UPG_BLOCK = 1;
    private static final int MAGIC = 4;
    private static final int UPGRADE_MAGIC = 1;

    public DiceBoulder() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BASE_BLOCK;
        baseSilly = silly = 1;
        SneckoMod.loadJokeCardImage(this, "DiceBoulder.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.addToBot(new ModifyBlockAction(this.uuid, this.magicNumber));
        this.addToBot(new ReduceCostActionFixed(this.uuid, 1));
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}