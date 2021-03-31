package champ.cards;

import champ.ChampMod;
import champ.powers.DualPlaySrikePower;
import champ.vfx.DaggerThrowAnyColorEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class BladeFlurry extends AbstractChampCard {

    public final static String ID = makeID("BladeFlurry");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 6;

    public BladeFlurry() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.TECHNIQUE);
        postInit();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new VFXAction(new DaggerThrowAnyColorEffect(m.hb.cX, m.hb.cY, Color.LIGHT_GRAY, 0F, -30F), 0.0F));
        atb(new VFXAction(new DaggerThrowAnyColorEffect(m.hb.cX, m.hb.cY, Color.LIGHT_GRAY, 0F, -30F), 0.0F));
        atb(new VFXAction(new DaggerThrowAnyColorEffect(m.hb.cX, m.hb.cY, Color.LIGHT_GRAY, 0F, -30F), 0.1F));

        atb(new DamageAction(m, makeInfo(), AbstractGameAction.AttackEffect.NONE));

        applyToSelf(new DualPlaySrikePower(p,1));

        techique();
    }


    public void upp() {
        upgradeDamage(3);
    }
}