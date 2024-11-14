package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.SneckoMod;
import sneckomod.actions.NoApplyRandomDamageAction;

public class DiceBoulder extends AbstractSneckoCard {

    public final static String ID = makeID("DiceBoulder");
    private static final int BASE_BLOCK = 7;
    private static final int UPG_BLOCK = 1;
    private static final int MAGIC = 3;
    private static final int UPGRADE_MAGIC = 1;

    public DiceBoulder() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        baseBlock = BASE_BLOCK;
        SneckoMod.loadJokeCardImage(this, "DiceBoulder.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.addToBot(new ModifyBlockAction(this.uuid, this.magicNumber));
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}