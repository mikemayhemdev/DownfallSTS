package automaton.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.vfx.SmallLaserEffectColored;

public class MinorBeam extends AbstractBronzeCard {

    public final static String ID = makeID("MinorBeam");

    //stupid intellij stuff attack, enemy, special

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;

    public MinorBeam() {
        super(ID, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        thisEncodes();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmallLaserEffectColored(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, Color.WHITE), 0.2F));
        }

        dmg(m, AbstractGameAction.AttackEffect.NONE);
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}