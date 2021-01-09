package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import charbosses.vfx.IronWaveReversedEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.IntimidateEffect;
import downfall.downfallMod;

import java.util.Iterator;

public class EnIntimidate extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Intimidate";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Intimidate");
    }

    public EnIntimidate() {
        super(ID, cardStrings.NAME, "red/skill/intimidate", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, AbstractMonster.Intent.DEBUFF);
        this.exhaust = true;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        artifactConsumedIfPlayed = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new SFXAction("INTIMIDATE"));
        this.addToBot(new VFXAction(p, new IntimidateEffect(m.hb.cX, m.hb.cY), 1.0F));
        this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, this.magicNumber + 1, false), this.magicNumber + 1, true, AbstractGameAction.AttackEffect.NONE));
 }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnIntimidate();
    }
}
