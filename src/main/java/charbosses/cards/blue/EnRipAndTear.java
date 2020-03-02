package charbosses.cards.blue;

import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.NewRipAndTearAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.BeamCell;
import com.megacrit.cardcrawl.cards.blue.RipAndTear;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.RipAndTearEffect;

import java.util.ArrayList;

public class EnRipAndTear extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:RipAndTear";
    private static final CardStrings cardStrings;

    public EnRipAndTear() {
        super(ID, cardStrings.NAME, "blue/attack/rip_and_tear", 1, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 7;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; ++i) {
                this.addToBot(new VFXAction(new RipAndTearEffect(p.hb.cX, p.hb.cY, Color.RED, Color.GOLD)));
                this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 2;
    }

    public AbstractCard makeCopy() {
        return new EnRipAndTear();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Rip and Tear");
    }
}