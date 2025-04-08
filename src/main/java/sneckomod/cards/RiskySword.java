package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;

public class RiskySword extends AbstractSneckoCard {

    public final static String ID = makeID("RiskySword");

    private static final int DAMAGE = 8;
    private static final int MAGIC = 8;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int UPGRADE_MAGIC = 2;

    public RiskySword() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "RiskySword.png");
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
    }

    @Override
    public void onMuddledSword() {
        flash();
        baseDamage += magicNumber;
        applyPowers();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
