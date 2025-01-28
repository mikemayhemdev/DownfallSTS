package sneckomod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import sneckomod.SneckoMod;

public class MintCondition extends AbstractSneckoCard {

    public final static String ID = makeID("MintCondition");

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    //this is a show-off clone

    public MintCondition() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        this.tags.add(SneckoMod.OVERFLOW);
        SneckoMod.loadJokeCardImage(this, "MintCondition.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (isOverflowActive(this)) {
            this.addToBot(new VFXAction(p, new InflameEffect(p), 1.0F));
            this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            initializeDescription();
        }
    }
}
