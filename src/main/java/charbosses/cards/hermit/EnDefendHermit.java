package charbosses.cards.hermit;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.Defend_Hermit;
import hermit.cards.Headshot;
import hermit.cards.Strike_Hermit;
import hermit.characters.hermit;

public class EnDefendHermit extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Defend_Hermit";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Defend_Hermit.ID);

    public EnDefendHermit() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/card_defend.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.BASIC, CardTarget.SELF, AbstractMonster.Intent.DEFEND);
        this.baseBlock = 5;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new GainBlockAction(m, m, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnDefendHermit();
    }
}
