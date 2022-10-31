package charbosses.cards.purple;

import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.WallopAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

import java.util.ArrayList;

public class EnWish extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Wish";
    private static final CardStrings cardStrings;

    public EnWish() {
        super(ID, cardStrings.NAME, "purple/skill/wish", 3, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.RARE, CardTarget.NONE, AbstractMonster.Intent.BUFF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        strengthGeneratedIfPlayed = 3;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCharBoss n = AbstractCharBoss.boss;
        this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.FIREBRICK, true)));
        this.addToBot(new VFXAction(n, new InflameEffect(n), 1.0F));
        this.addToBot(new ApplyPowerAction(n, n, new StrengthPower(n, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            strengthGeneratedIfPlayed++;
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() * 2;
    }

    public AbstractCard makeCopy() {
        return new EnWish();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("BecomeAlmighty");
    }
}
