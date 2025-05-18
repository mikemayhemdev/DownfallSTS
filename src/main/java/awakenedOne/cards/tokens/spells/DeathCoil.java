package awakenedOne.cards.tokens.spells;

import awakenedOne.relics.EyeOfTheOccult;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.att;
import static awakenedOne.util.Wiz.vfx;

public class DeathCoil extends AbstractSpellCard {
    public final static String ID = makeID(DeathCoil.class.getSimpleName());
    // intellij stuff attack, enemy, 13, 6, , , ,

    public DeathCoil() {
        super(ID, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 9;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)){
            target = CardTarget.ALL_ENEMY;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // this.addToTop(new LoseHPAction(p, p, magicNumber));
        if (!AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            if (m != null) {
                this.addToBot(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
            }
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        } else {
            AbstractDungeon.player.getRelic(EyeOfTheOccult.ID).flash();

            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    if (monster != null) {
                        this.addToBot(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, monster.hb.cX, monster.hb.cY), 0.5F));
                    }
                }
              }
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));

        }
    }

    public void upp() {
       // upgradeMagicNumber(-1);
        upgradeDamage(3);
    }
}