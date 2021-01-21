package charbosses.cards.crowbot;

import basemod.abstracts.CustomCard;
import charbosses.bosses.Crowbot.CharBossCrowbot;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class Barrier extends CustomCard {
    public static final String ID = "downfall_Charboss:Barrier";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public Barrier() {
        super(ID, Barrier.cardStrings.NAME, "downfallResources/images/cards/crowbot/barrier.png", 1, Barrier.cardStrings.DESCRIPTION, CardType.SKILL,CharBossCrowbot.Enums.Crowbot_COLOR, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 11;

    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (AbstractDungeon.player.currentBlock == 0) {
            addToBot(new GainBlockAction(p, p, this.block));
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
        return new Barrier();
    }
}
