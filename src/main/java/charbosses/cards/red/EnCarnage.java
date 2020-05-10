package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;

public class EnCarnage extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Carnage";
    private static final CardStrings cardStrings;

    public EnCarnage() {
        super(ID, cardStrings.NAME, "red/attack/carnage", 2, cardStrings.DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 20;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int i;
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(new ViolentAttackEffect(p.hb.cX, p.hb.cY, Color.RED)));

            for(i = 0; i < 5; ++i) {
                this.addToBot(new VFXAction(new StarBounceEffect(p.hb.cX, p.hb.cY)));
            }
        } else {
            this.addToBot(new VFXAction(new ViolentAttackEffect(p.hb.cX, p.hb.cY, Color.RED), 0.4F));

            for(i = 0; i < 5; ++i) {
                this.addToBot(new VFXAction(new StarBounceEffect(p.hb.cX, m.hb.cY)));
            }
        }

        this.addToBot(new DamageAction(p, new DamageInfo(m, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(8);
        }

    }

    public AbstractCard makeCopy() {
        return new EnCarnage();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Carnage");
    }
}
