package awakenedOne.cards.tokens.spells;

import awakenedOne.relics.EyeOfTheOccult;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Darkleech extends AbstractSpellCard {
    public final static String ID = makeID(Darkleech.class.getSimpleName());
    // intellij stuff skill, all_enemy, , , , , 7, 2

    public Darkleech() {
        super(ID, 0, CardType.SKILL, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, ID+".png");
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if(AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)){
            target = CardTarget.ALL_ENEMY;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)){
            this.addToBot(new VFXAction(new GiantEyeEffect(m.hb.cX, m.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
            HexCurse(magicNumber, m, p);
        }
        else {
            //AbstractDungeon.player.getRelic(EyeOfTheOccult.ID).flash();
            AbstractDungeon.getMonsters().monsters.stream().filter(m2 -> !m2.isDead && !m2.isDying).forEach(m2 -> {
                this.addToBot(new VFXAction(new GiantEyeEffect(m2.hb.cX, m2.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
                HexCurse(magicNumber, m2, p);
            });
        }
        atb(new GainEnergyAction(1));
        //this.addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, 1), 1));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}