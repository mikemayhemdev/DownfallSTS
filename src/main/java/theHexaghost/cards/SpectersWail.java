package theHexaghost.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theHexaghost.HexaMod;

public class SpectersWail extends AbstractHexaCard {

    public final static String ID = makeID("SpectersWail");

    //stupid intellij stuff ATTACK, ALL_ENEMY, COMMON

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;

    public SpectersWail() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isEthereal = true;
        isMultiDamage = true;
        tags.add(HexaMod.AFTERLIFE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        for (int q = 0; q < 6; q++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1.0f), ShockWaveEffect.ShockWaveType.NORMAL)));
        }
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    public void triggerOnExhaust() {
        this.addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
        for (int q = 0; q < 6; q++) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, new Color(MathUtils.random(1.0f), MathUtils.random(1.0f), MathUtils.random(1.0f), 1.0f), ShockWaveEffect.ShockWaveType.NORMAL)));
        }
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }



        public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}