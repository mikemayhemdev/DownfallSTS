package charbosses.cards.crowbot;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Crowbot.CharBossCrowbot;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.blue.EnReinforcedBody;
import charbosses.ui.EnemyEnergyPanel;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class EnBarrier extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Barrier";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public EnBarrier() {
        super(ID, EnBarrier.cardStrings.NAME, "crowbot/barrier", 1, EnBarrier.cardStrings.DESCRIPTION, CardType.SKILL,CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND,true);
        this.baseBlock = 11;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if(AbstractCharBoss.boss.currentBlock == 0 ){
            this.addToBot(new GainBlockAction(m, m, this.block));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnBarrier();
    }
}
