package charbosses.cards.purple;

import charbosses.actions.common.EnemyNotStanceCheckAction;
import charbosses.actions.unique.EnemyChangeStanceAction;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.actions.watcher.NotStanceCheckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.purple.EmptyBody;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.EmptyStanceEffect;

import java.util.ArrayList;

public class EnEmptyBody extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:EmptyBody";
    private static final CardStrings cardStrings;

    public EnEmptyBody() {
        super(ID, cardStrings.NAME, "purple/skill/empty_body", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.PURPLE, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 7;
        this.tags.add(CardTags.EMPTY);
        this.energyGeneratedIfPlayed = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new EnemyNotStanceCheckAction(new VFXAction(new EmptyStanceEffect(m.hb.cX, m.hb.cY), 0.1F)));
        this.addToBot(new EnemyChangeStanceAction("Neutral"));
    }

    public AbstractCard makeCopy() {
        return new EnEmptyBody();
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }

    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        if (AbstractCharBoss.boss.stance.ID == "Calm") {
            this.energyGeneratedIfPlayed = 2;
            return autoPriority() + 20;
        } else {
            this.energyGeneratedIfPlayed = 0;
        }
        return super.getPriority(hand);
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("EmptyBody");
    }
}
