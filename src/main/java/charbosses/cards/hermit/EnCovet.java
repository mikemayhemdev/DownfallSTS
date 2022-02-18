package charbosses.cards.hermit;

import charbosses.cards.curses.EnPain;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.Covet;
import hermit.characters.hermit;

import java.util.stream.Collectors;

public class EnCovet extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Covet";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Covet.ID);

    public EnCovet() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/covet.png", 0, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.BASIC, CardTarget.SELF, AbstractMonster.Intent.NONE);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (this.owner.hand.group.stream().anyMatch(c -> c.cardID == EnPain.ID)) {
            AbstractCard enPain = this.owner.hand.group.stream().filter(c -> c.cardID == EnPain.ID).collect(Collectors.toList()).get(0);
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(enPain, this.owner.hand));
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnCovet();
    }
}
