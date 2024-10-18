package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;

public class IronFang extends AbstractSneckoCard {

    public final static String ID = SneckoMod.makeID("IronFang");

    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 1;

    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK = 1;

    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public IronFang() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "IronFang.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false), magicNumber));
        addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.1F));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeBlock(UPGRADE_BLOCK);
            upgradeMagicNumber(UPGRADE_MAGIC);
            initializeDescription();
        }
    }
}
