package charbosses.cards.blue;

import charbosses.actions.orb.EnemyChannelAction;
import charbosses.cards.AbstractBossCard;
import charbosses.orbs.AbstractEnemyOrb;
import charbosses.orbs.EnemyLightning;
import charbosses.orbs.EnemyPlasma;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import java.util.ArrayList;

public class EnMeteorStrike extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Meteor Strike";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Meteor Strike");
    }

    public EnMeteorStrike() {
        super(ID, cardStrings.NAME, "blue/attack/meteor_strike", 5, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK_BUFF);
        this.showEvokeValue = true;
        this.showEvokeOrbCount = 3;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        this.baseDamage = 24;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) { // TODO: SFX
        if (p != null) {
            this.addToBot(new VFXAction(new WeightyImpactEffect(p.hb.cX, p.hb.cY)));
        }
        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        for (int i = 0; i < this.magicNumber; ++i) {
            this.addToBot(new EnemyChannelAction(new EnemyPlasma()));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(6);
        }

    }// 51

    public AbstractCard makeCopy() {
        return new EnMeteorStrike();
    }
}