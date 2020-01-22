package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;
import theHexaghost.ghostflames.BolsteringGhostflame;
import theHexaghost.ghostflames.CrushingGhostflame;

public class CrushingStance extends AbstractHexaCard {

    public final static String ID = makeID("CrushingStance");

    //stupid intellij stuff ATTACK, SELF_AND_ENEMY, RARE

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;

    public CrushingStance() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.SLASH_HEAVY);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractGhostflame gf = new CrushingGhostflame(GhostflameHelper.activeGhostFlame.lx, GhostflameHelper.activeGhostFlame.ly);
                GhostflameHelper.hexaGhostFlames.set(GhostflameHelper.hexaGhostFlames.indexOf(GhostflameHelper.activeGhostFlame), gf);
                gf.activate();
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}