package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import downfall.downfallMod;
import sneckomod.SneckoMod;

public class Serpentscale extends AbstractSneckoCard {

    public final static String ID = makeID("Serpentscale");

    // Card constants
    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    public Serpentscale() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        this.tags.add(SneckoMod.OVERFLOW);
        SneckoMod.loadJokeCardImage(this, "Serpentscale.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);

        if (!isOverflowActive(this)) {
            addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, 1), 1));
        }
        if (isOverflowActive(this)) {
            addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p, magicNumber), magicNumber));
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }

}