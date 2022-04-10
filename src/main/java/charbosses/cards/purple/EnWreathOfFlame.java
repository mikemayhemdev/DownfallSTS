package charbosses.cards.purple;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.general.EnemyVigorPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

public class EnWreathOfFlame extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:WreathOfFlame";
    private static final CardStrings cardStrings;

    public EnWreathOfFlame() {
        super(ID, cardStrings.NAME, "purple/skill/wreathe_of_flame", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(m, new FlameBarrierEffect(m.hb.cX, m.hb.cY), 0.1F));
        } else {
            this.addToBot(new VFXAction(m, new FlameBarrierEffect(m.hb.cX, m.hb.cY), 0.5F));
        }
        this.addToTop(new ApplyPowerAction(this.owner, this.owner, new EnemyVigorPower(this.owner, magicNumber), magicNumber));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }

    }

    public AbstractCard makeCopy() {
        return new EnWreathOfFlame();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("WreathOfFlame");
    }
}
