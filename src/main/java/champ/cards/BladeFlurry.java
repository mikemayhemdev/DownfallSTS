package champ.cards;

import champ.ChampMod;
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

    private static final int DAMAGE = 4;

    public BladeFlurry() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = 1;
                if (upgraded) x++;
                for (AbstractCard q : p.hand.group) if (q.hasTag(CardTags.STRIKE)) x++;
                for (int i = 0; i < x; i++) {
                    att(new DamageAction(m, makeInfo(), AttackEffect.NONE));
                    att(new VFXAction(new DaggerThrowAnyColorEffect(m.hb.cX, m.hb.cY, Color.LIGHT_GRAY, 0F, -30F), 0.0F));
                    att(new VFXAction(new DaggerThrowAnyColorEffect(m.hb.cX, m.hb.cY, Color.LIGHT_GRAY, 0F, -30F), 0.0F));
                    att(new VFXAction(new DaggerThrowAnyColorEffect(m.hb.cX, m.hb.cY, Color.LIGHT_GRAY, 0F, -30F), 0.0F));
                }
            }
        });
        finisher();
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player != null) {
            int x = 0;
            if (upgraded){
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                x++;
            } else {
                this.rawDescription = cardStrings.DESCRIPTION;
            }
            for (AbstractCard q : AbstractDungeon.player.hand.group) if (q.hasTag(CardTags.STRIKE)) x++;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + x;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        if (upgraded){
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } else {
            this.rawDescription = cardStrings.DESCRIPTION;
        }
        this.initializeDescription();
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}