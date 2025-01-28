package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;
import sneckomod.powers.TyphoonPlusPower;
import sneckomod.powers.TyphoonPower;

public class TyphoonFang extends AbstractSneckoCard {

    public final static String ID = makeID("TyphoonFang");

    //stupid intellij stuff ATTACK, ENEMY, BASIC

    // this card was a herculean effort to code

    private static final int DAMAGE = 12;

    public TyphoonFang() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        //   baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "TyphoonFang.png");
        this.tags.add(SneckoMod.OVERFLOW);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// reused snek bite animation
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        if (isOverflowActive(this) && !this.purgeOnUse) {
            if (!upgraded) {
                applyToSelf(new TyphoonPower(1));
            }
            if (upgraded) {
                applyToSelf(new TyphoonPlusPower(1));
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(4);
        }
    }
}
