package awakenedOne.cards.tokens.spells;

import awakenedOne.powers.AphoticFountPower;
import awakenedOne.relics.EyeOfTheOccult;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Cryostasis extends AbstractSpellCard {
    public final static String ID = makeID(Cryostasis.class.getSimpleName());

    public Cryostasis() {
        super(ID, 1, CardType.SKILL, CardTarget.ENEMY);
        baseBlock = 8;
        baseMagicNumber = magicNumber = 1;
        this.setBackgroundTexture("awakenedResources/images/512/bg_skill_awakened.png", "awakenedResources/images/1024/bg_skill_awakened.png");
        loadJokeCardImage(this, makeBetaCardPath(Cryostasis.class.getSimpleName() + ".png"));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            target = CardTarget.ALL_ENEMY;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
        AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(p.hb.cX, p.hb.cY));
        blck();

        // if (upgraded) {
        if (!AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            atb(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.LIGHTNING));
        }

        if (AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            //AbstractDungeon.player.getRelic(EyeOfTheOccult.ID).flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    addToBot(new ApplyPowerAction(monster, p, new WeakPower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.LIGHTNING));
                    //    }
                }
            }
        }

        if (AbstractDungeon.player.hasPower(AphoticFountPower.POWER_ID)){
            AbstractDungeon.player.getPower(AphoticFountPower.POWER_ID).onSpecificTrigger();
            }
        }




    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}