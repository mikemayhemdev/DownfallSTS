package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import sneckomod.SneckoMod;

public class Cower extends AbstractSneckoCard {

    public static final String ID = SneckoMod.makeID("Cower");

    private static final int BLOCK = 18;
    private static final int UPG_BLOCK = 4;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public Cower() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        SneckoMod.loadJokeCardImage(this, "Cower.png");
        magicNumber = baseMagicNumber = MAGIC;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void onMuddledSword() {
        flash();
        AbstractPlayer p = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
        addToBot(new ApplyPowerAction(p, p, new WeakPower(p, magicNumber, false), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}
