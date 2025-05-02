package awakenedOne.cards.tokens.spells;

import awakenedOne.relics.EyeOfTheOccult;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.att;
import static awakenedOne.util.Wiz.vfx;

public class Thunderbolt extends AbstractSpellCard {
    public final static String ID = makeID(Thunderbolt.class.getSimpleName());
    // intellij stuff attack, enemy, 13, 6, , , , 

    public Thunderbolt() {
        super(ID, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 3;
        baseSecondDamage = 2;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)){
            target = CardTarget.ALL_ENEMY;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new SFXAction("ORB_LIGHTNING_EVOKE", 0.1F));
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        if (!AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)){
            vfx(new LightningEffect(m.hb.cX, m.hb.cY));
            dmg(m, AbstractGameAction.AttackEffect.NONE);}
        else {
            AbstractDungeon.player.getRelic(EyeOfTheOccult.ID).flash();
            AbstractMonster mo;
            while(var3.hasNext()) {
                mo = (AbstractMonster)var3.next();
                if (!mo.isDeadOrEscaped()) {
                    this.addToBot(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05F));
                }
            }
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }

    }

    public void upp() {
        upgradeDamage(3);
    }
}