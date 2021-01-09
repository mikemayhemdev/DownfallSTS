package charbosses.cards.blue;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ModifyBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Skim;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnSteamBarrier extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:SteamBarrier";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Steam");
    }

    public EnSteamBarrier() {
        super(ID, cardStrings.NAME, "blue/skill/steam_barrier", 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 6;
    }

    public EnSteamBarrier(int blockModifier){
        this();
        this.baseBlock -= blockModifier;
        this.block = this.baseBlock;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new ModifyBlockAction(this.uuid, -1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
        }
    }// 51

    public AbstractCard makeCopy() {
        return new EnSteamBarrier();
    }
}