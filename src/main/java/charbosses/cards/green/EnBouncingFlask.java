package charbosses.cards.green;

import charbosses.actions.unique.EnemyBouncingFlaskAction;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.BouncingFlask;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;

import java.util.ArrayList;

public class EnBouncingFlask extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Bouncing Flask";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(BouncingFlask.ID);
    }

    public EnBouncingFlask() {
        super(ID, EnBouncingFlask.cardStrings.NAME, "green/skill/bouncing_flask", 2, EnBouncingFlask.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.STRONG_DEBUFF);
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        artifactConsumedIfPlayed = 3;
        if (upgraded)
            artifactConsumedIfPlayed = 4;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return magicNumber * 4;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new VFXAction(new PotionBounceEffect(m.hb.cX, m.hb.cY, p.hb.cX, this.hb.cY), 0.4F));// 41
        this.addToBot(new EnemyBouncingFlaskAction(4, m, this.magicNumber));// 43
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
        return new EnBouncingFlask();
    }
}
