package awakenedOne.cards.tokens.spells;

import awakenedOne.powers.ManaburnPower;
import awakenedOne.relics.EyeOfTheOccult;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import hermit.powers.Drained;

import static awakenedOne.AwakenedOneMod.*;

public class DeathCoil extends AbstractSpellCard {
    public final static String ID = makeID(DeathCoil.class.getSimpleName());
    // intellij stuff attack, enemy, 13, 6, , , ,

    public DeathCoil() {
        super(ID, 0, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 10;
        baseMagicNumber = magicNumber = 4;
        loadJokeCardImage(this, makeBetaCardPath(DeathCoil.class.getSimpleName() + ".png"));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)){
            target = CardTarget.ALL_ENEMY;
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // this.addToTop(new LoseHPAction(p, p, magicNumber));
        if (!AbstractDungeon.player.hasRelic(EyeOfTheOccult.ID)) {
            if (m != null) {
                this.addToTop(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
            }
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
            if (upgraded) {
                dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
            }
            this.addToBot(new ApplyPowerAction(m, p, new ManaburnPower(m, this.magicNumber), this.magicNumber));
        } else {
            //AbstractDungeon.player.getRelic(EyeOfTheOccult.ID).flash();
            this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            if (upgraded) {
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
            }
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if (!monster.isDead && !monster.isDying) {
                    if (monster != null) {
                        this.addToTop(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, monster.hb.cX, monster.hb.cY), 0.5F));
                        this.addToBot(new ApplyPowerAction(monster, p, new ManaburnPower(m, this.magicNumber), this.magicNumber));
                    }
                }
              }
        }
        this.addToBot(new ApplyPowerAction(p, p, new Drained(p,p, 1), 1));
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeDamage(-5);
    }
}