package awakenedOne.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import expansioncontent.actions.BloodthirstAction;

import static awakenedOne.AwakenedOneMod.*;

public class Bloodthirst extends AbstractAwakenedCard {
    public final static String ID = makeID(Bloodthirst.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public Bloodthirst() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 20;
        tags.add(CardTags.HEALING);
        loadJokeCardImage(this, makeBetaCardPath(Bloodthirst.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int i;
        if (Settings.FAST_MODE) {
            this.addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED)));

            for(i = 0; i < 5; ++i) {
                this.addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        } else {
            this.addToTop(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED), 0.4F));

            for(i = 0; i < 5; ++i) {
                this.addToTop(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
            }
        }
        this.addToBot(new BloodthirstAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.uuid, true));
    }

    public void upp() {
        upgradeDamage(5);
    }
}