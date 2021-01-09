package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleRandomCardAction;

public class QuickBite extends AbstractSneckoCard {

    public final static String ID = makeID("QuickBite");

    //stupid intellij stuff ATTACK, ENEMY, COMMON

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public QuickBite() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(SneckoMod.RNG);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));// 117
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        int x = getRandomNum(magicNumber, 2, this);

        if (x > 0)
            atb(new DrawCardAction(x));

        atb(new MuddleRandomCardAction(1, true));

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}