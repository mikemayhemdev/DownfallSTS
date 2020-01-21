package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.BurnAction;
import theHexaghost.actions.ExtinguishAction;
import theHexaghost.ghostflames.AbstractGhostflame;

public class GhostflameInferno extends AbstractHexaCard {

    public final static String ID = makeID("GhostflameInferno");

    //stupid intellij stuff ATTACK, ALL_ENEMY, RARE

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 3;

    private static final int MAGIC = 6;
    private static final int UPG_MAGIC = 3;

    public GhostflameInferno() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractGhostflame gf : GhostflameHelper.hexaGhostFlames) {
            if (gf.charged) {
                atb(new ExtinguishAction(gf));
                allDmg(AbstractGameAction.AttackEffect.FIRE);
                for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    atb(new BurnAction(q, magicNumber));
                }
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}