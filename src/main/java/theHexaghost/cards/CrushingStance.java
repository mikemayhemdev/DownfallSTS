package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.ghostflames.AbstractGhostflame;
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
        tags.add(HexaMod.GHOSTWHEELCARD);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        addToBot(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);
        atb(new AbstractGameAction() {
            @Override
            public void update() {
             //   HexaMod.renderFlames = true;
            //    isDone = true;
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